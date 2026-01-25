package virtuoel.pehkui.mixin.world.entity;

import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Mob.class)
public abstract class MobMixin {
	@ModifyArg(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;causeExtraKnockback(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/phys/Vec3;)V"))
	private float pehkui$tryAttack$knockback(float value) {
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);

		return scale != 1.0F ? scale * value : value;
	}

	@WrapOperation(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
	private AABB pehkui$tickMovement$expand(AABB obj, double x, double y, double z, Operation<AABB> original) {
		final float widthScale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale((Entity) (Object) this);

		if (widthScale != 1.0F) {
			x *= widthScale;
			z *= widthScale;
		}

		if (heightScale != 1.0F) {
			y *= heightScale;
		}

		return original.call(obj, x, y, z);
	}

	@WrapOperation(method = "getAttackBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
	private AABB pehkui$getAttackBox$expand(AABB obj, double x, double y, double z, Operation<AABB> original) {
		final float scale = ScaleUtils.getEntityReachScale((Entity) (Object) this);

		if (scale != 1.0F) {
			x *= scale;
			z *= scale;
		}

		return original.call(obj, x, y, z);
	}

	@Inject(at = @At("RETURN"), method = "convertTo(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/ConversionParams;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/ConversionParams$AfterConversion;)Lnet/minecraft/world/entity/Mob;")
	private <T extends Mob> void pehkui$convertTo(EntityType<T> entityType, ConversionParams conversionParams, EntitySpawnReason entitySpawnReason, ConversionParams.AfterConversion<T> afterConversion, CallbackInfoReturnable<T> cir) {
		final Mob e = cir.getReturnValue();

		if (e != null) {
			ScaleUtils.loadScale(e, (Entity) (Object) this);
		}
	}
}
