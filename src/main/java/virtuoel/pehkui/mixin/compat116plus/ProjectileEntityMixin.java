package virtuoel.pehkui.mixin.compat116plus;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Projectile.class)
public abstract class ProjectileEntityMixin
{
	@ModifyExpressionValue(method = "checkLeftOwner", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(D)Lnet/minecraft/world/phys/AABB;"))
	private AABB pehkui$shouldLeaveOwner$expand(AABB value)
	{
		final Entity self = (Entity) (Object) this;
		final float width = ScaleUtils.getBoundingBoxWidthScale(self);
		final float height = ScaleUtils.getBoundingBoxHeightScale((self));

		if (width != 1.0F || height != 1.0F)
		{
			return value.inflate(width - 1.0D, height - 1.0D, width - 1.0D);
		}
		
		return value;
	}
	
	@ModifyVariable(method = "shoot(DDDFF)V", ordinal = 0, argsOnly = true, at = @At("HEAD"))
	private float pehkui$setVelocity$power(float value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale != 1.0F ? value * scale : value;
	}
	
	@Inject(at = @At("HEAD"), method = "setOwner(Lnet/minecraft/world/entity/Entity;)V")
	private void pehkui$setOwner(@Nullable Entity entity, CallbackInfo ci)
	{
		if (entity != null)
		{
			ScaleUtils.setScaleOfProjectile((Entity) (Object) this, entity);
		}
	}
}
