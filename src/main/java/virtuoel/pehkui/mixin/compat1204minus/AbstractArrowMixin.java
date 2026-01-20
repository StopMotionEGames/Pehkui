package virtuoel.pehkui.mixin.compat1204minus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin
{
	@Dynamic
	@ModifyArg(method = "tick", index = 1, at = @At(value = "INVOKE", target = MixinConstants.PERSISTENT_PROJECTILE_ENTITY_SET_VELOCITY))
	private double pehkui$tick$gravity(double value)
	{
		final Vec3 velocity = ((Entity) (Object) this).getDeltaMovement();
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale != 1.0F ? velocity.y + (scale * (value - velocity.y)) : value;
	}
}
