package virtuoel.pehkui.mixin.compat1192minus.compat116plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LargeFireball.class)
public abstract class LargeFireballMixin
{
	@Dynamic
	@ModifyArg(method = "onHit(Lnet/minecraft/world/phys/HitResult;)V", at = @At(value = "INVOKE", target = MixinConstants.CREATE_EXPLOSION_OPTIONAL_FIRE))
	private float pehkui$onCollision$createExplosion(float power)
	{
		final float scale = ScaleUtils.getExplosionScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			return power * scale;
		}
		
		return power;
	}
}
