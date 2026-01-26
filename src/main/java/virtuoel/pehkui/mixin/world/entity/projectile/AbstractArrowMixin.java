package virtuoel.pehkui.mixin.world.entity.projectile;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {
	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;<init>(Lnet/minecraft/world/entity/EntityType;DDDLnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V"),
		method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V",
		index = 2
	)
	private static double pehkui$construct$yOffset(double d, @Local(argsOnly = true) LivingEntity owner) {
		float scale = ScaleUtils.getEyeHeightScale(owner);

		return owner.getEyeY() - (0.1D * scale);
	}

	@Inject(at = @At("HEAD"), method = "setOwner")
	private void pehkui$setOwner(@Nullable Entity entity, CallbackInfo info) {
		if (entity != null) {
			ScaleUtils.setScaleOfProjectile((Entity) (Object) this, entity);
		}
	}

	@ModifyArg(method = "shouldFall", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(D)Lnet/minecraft/world/phys/AABB;"))
	private double pehkui$shouldFall$inflate(double d){
		final float scale = ScaleUtils.getModelHeightScale((Entity) (Object) this);

		return scale != 1.0F ? d * scale : d;
	}

	@ModifyArg(method = "findHitEntity", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getEntityHitResult(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Lnet/minecraft/world/phys/EntityHitResult;"))
	private AABB pehkui$findHitEntity$inflate(AABB box) {
		Projectile projectile = (Projectile) (Object) this;

		final float width = ScaleUtils.getBoundingBoxWidthScale(projectile);
		final float height = ScaleUtils.getBoundingBoxHeightScale(projectile);

		if (width != 1.0F || height != 1.0F) {
			return box.inflate(width - 1.0D, height - 1.0D, width - 1.0D);
		}

		return box;
	}

	@ModifyVariable(method = "onHitEntity", at = @At(value = "STORE"))
	private float pehkui$onHitEntity(float value) {
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);

		return scale != 1.0F ? ScaleUtils.divideClamped(value, scale) : value;
	}

	@ModifyArg(method = "onHitBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;scale(D)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$onHitBlock(double value) {
		final float scale = ScaleUtils.getModelHeightScale((Entity) (Object) this);
		return scale != 1.0F ? value * scale : value;
	}

	@ModifyReturnValue(method = "getDefaultGravity", at = @At(value = "RETURN"))
	private double pehkui$getDefaultGravity(double gravity) {
		float scale = ScaleUtils.getMotionScale((Entity) (Object) this);

		return scale != 1.0F ? gravity * scale : gravity;
	}
}
