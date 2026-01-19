package virtuoel.pehkui.mixin.compat1219plus;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin
{
	@ModifyExpressionValue(method = "hasLeftOwner", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Box;expand(D)Lnet/minecraft/util/math/Box;"))
	private Box pehkui$shouldLeaveOwner$expand(Box value)
	{
		final Entity self = (Entity) (Object) this;
		final float width = ScaleUtils.getBoundingBoxWidthScale(self);
		final float height = ScaleUtils.getBoundingBoxHeightScale((self));

		if (width != 1.0F || height != 1.0F)
		{
			return value.expand(width - 1.0D, height - 1.0D, width - 1.0D);
		}
		
		return value;
	}
	
	@ModifyVariable(method = "setVelocity(DDDFF)V", ordinal = 0, argsOnly = true, at = @At("HEAD"))
	private float pehkui$setVelocity$power(float value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale != 1.0F ? value * scale : value;
	}
	
	@Inject(at = @At("HEAD"), method = "setOwner(Lnet/minecraft/entity/Entity;)V")
	private void pehkui$setOwner(@Nullable Entity entity, CallbackInfo ci)
	{
		if (entity != null)
		{
			ScaleUtils.setScaleOfProjectile((Entity) (Object) this, entity);
		}
	}
}
