package virtuoel.pehkui.mixin.world.entity.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerMixin
{
	@ModifyArg(method = "attack(Lnet/minecraft/world/entity/Entity;)V", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeExtraKnockback(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/phys/Vec3;)V"))
	private float pehkui$attack$knockback(float strength)
	{
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * strength : strength;
	}

	@ModifyReturnValue(method = "getFlyingSpeed", at = @At(value = "RETURN", ordinal = 0))
	private float pehkui$getOffGroundSpeed(float original)
	{
		final float scale = ScaleUtils.getFlightScale((Player) (Object) this);

		return scale != 1.0F ? original * scale : original;
	}

	@ModifyReturnValue(method = "blockInteractionRange", at = @At("RETURN"))
	private double pehkui$getBlockInteractionRange(double original)
	{
		final float scale = ScaleUtils.getBlockReachScale((Entity) (Object) this);
		return scale != 1.0F ? scale * original : original;
	}

	@ModifyReturnValue(method = "entityInteractionRange", at = @At("RETURN"))
	private double pehkui$getEntityInteractionRange(double original)
	{
		final float scale = ScaleUtils.getEntityReachScale((Entity) (Object) this);
		return scale != 1.0F ? scale * original : original;
	}

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

	// todo: see if entity reach doesn't break. And see if the method above is doing that incorrectly
//	@ModifyExpressionValue(method = "attack", at = @At(value = "CONSTANT", args = "doubleValue=9.0F"))
//	private double pehkui$attack$distance(double value)
//	{
//		final float scale = ScaleUtils.getEntityReachScale((Entity) (Object) this);
//
//		return scale > 1.0F ? scale * scale * value : value;
//	}
}
