package virtuoel.pehkui.mixin.compat115minus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ThrowableProjectile.class)
public abstract class ThrowableProjectileMixin
{
	@Dynamic
	@ModifyArg(method = MixinConstants.SET_VELOCITY, at = @At(value = "INVOKE", target = MixinConstants.MULTIPLY))
	private double pehkui$setVelocity$multiply(double value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			return value * scale;
		}
		
		return value;
	}
}
