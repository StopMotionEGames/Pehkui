package virtuoel.pehkui.mixin.world.entity.projectile;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Projectile.class)
public abstract class ProjectileMixin {
	@ModifyExpressionValue(method = "checkLeftOwner", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(D)Lnet/minecraft/world/phys/AABB;"))
	private AABB pehkui$checkLeftOwner$expand(AABB value) {
		final Entity self = (Entity) (Object) this;
		final float width = ScaleUtils.getBoundingBoxWidthScale(self);
		final float height = ScaleUtils.getBoundingBoxHeightScale((self));

		if (width != 1.0F || height != 1.0F) {
			return value.inflate(width - 1.0D, height - 1.0D, width - 1.0D);
		}

		return value;
	}

	@ModifyArg(method = "shoot(DDDFF)V", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;getMovementToShoot(DDDFF)Lnet/minecraft/world/phys/Vec3;"))
	private float pehkui$shoot$power(float g) {
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);

		return scale != 1.0F ? g * scale : g;
	}

	@Inject(at = @At("HEAD"), method = "setOwner(Lnet/minecraft/world/entity/Entity;)V")
	private void pehkui$setOwner$head(@Nullable Entity entity, CallbackInfo ci) {
		if (entity != null) {
			ScaleUtils.setScaleOfProjectile((Entity) (Object) this, entity);
		}
	}

	@Inject(at = @At("RETURN"), method = "setOwner(Lnet/minecraft/world/entity/Entity;)V")
	private void pehkui$setOwner$return(Entity entity, CallbackInfo ci, @Local(argsOnly = true) Entity owner) {
		ScaleUtils.setScaleOfProjectile((Entity) (Object) this, owner);
	}
}
