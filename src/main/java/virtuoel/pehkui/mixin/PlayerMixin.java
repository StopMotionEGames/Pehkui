package virtuoel.pehkui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerMixin {
	@WrapOperation(method = "aiStep()V", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
	private AABB pehkui$tickMovement$expand(AABB obj, double x, double y, double z, Operation<AABB> original) {
		Entity entity = (Entity) (Object) this;
		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);

		if (widthScale != 1.0F) {
			x *= widthScale;
			z *= widthScale;
		}

		if (heightScale != 1.0F) {
			y *= heightScale;
		}

		return original.call(obj, x, y, z);
	}

	@ModifyExpressionValue(method = "attack(Lnet/minecraft/world/entity/Entity;)V", at = {@At(value = "CONSTANT", args = "floatValue=0.5F", ordinal = 0), @At(value = "CONSTANT", args = "floatValue=0.5F", ordinal = 1)})
	private float pehkui$attack$knockback(float value) {
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);

		return scale != 1.0F ? scale * value : value;
	}

	@ModifyExpressionValue(method = "getCurrentItemAttackStrengthDelay", at = @At(value = "CONSTANT", args = "doubleValue=20.0D"))
	private double pehkui$getAttackCooldownProgressPerTick$multiplier(double value) {
		final float scale = ScaleUtils.getAttackSpeedScale((Entity) (Object) this);

		return scale != 1.0F ? value / scale : value;
	}

	@ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
	private float pehkui$getBlockBreakingSpeed(float original) {
		final float scale = ScaleUtils.getMiningSpeedScale((Entity) (Object) this);

		return scale != 1.0F ? original * scale : original;
	}

	// todo! See if cape breaks. If break, found the right place where capes are updated
//	@ModifyExpressionValue(method = "updateCapeAngles", at = { @At(value = "CONSTANT", args = "doubleValue=10.0D"), @At(value = "CONSTANT", args = "doubleValue=-10.0D") })
//	private double pehkui$updateCapeAngles$limits(double value)
//	{
//		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
//
//		return scale != 1.0F ? scale * value : value;
//	}

	// todo: see if nothing breaks here
//	@WrapOperation(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Box;expand(DDD)Lnet/minecraft/util/math/Box;"))
//	private Box pehkui$attack$expand(Box obj, double x, double y, double z, Operation<Box> original, @Local(argsOnly = true) Entity target) {
//		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(target);
//		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(target);
//
//		if (widthScale != 1.0F) {
//			x *= widthScale;
//			z *= widthScale;
//		}
//
//		if (heightScale != 1.0F) {
//			y *= heightScale;
//		}
//
//		return original.call(obj, x, y, z);
//	}
}
