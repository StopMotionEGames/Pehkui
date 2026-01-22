package virtuoel.pehkui.mixin.world.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.api.PehkuiConfig;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	private BlockPos blockPosition;

	@Unique
	protected void setPosDirectly(final BlockPos pos) {
		blockPosition = pos;
	}

	@ModifyArg(method = "getPassengerRidingPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getPassengerAttachmentPoint(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/EntityDimensions;F)Lnet/minecraft/world/phys/Vec3;"))
	private float pehkui$getPassengerRidingPos$getPassengerAttachmentPos(float value) {
		final float scale = ScaleUtils.getBoundingBoxHeightScale((Entity) (Object) this);
		return scale == 1.0F ? value : value * scale;
	}

	@ModifyReturnValue(method = "getGravity", at = @At("RETURN"))
	private double pehkui$getFinalGravity(double original) {
		if (original == 0.0D) {
			return 0.0D;
		}

		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		return scale != 1.0F ? original : original * scale;
	}

	@ModifyExpressionValue(method = "checkSupportingBlock", at = @At(value = "CONSTANT", args = "doubleValue=1.0E-6"))
	private double pehkui$updateSupportingBlockPos$offset(double value) {
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);

		return scale < 1.0F ? value * scale : value;
	}

	@ModifyArg(method = "checkFallDamage",index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;D)V"))
	private double pehkui$fall$fallDistance(double distance) {
		final float scale = ScaleUtils.getFallingScale((Entity) (Object) this);

		if (scale != 1.0F) {
			if (PehkuiConfig.COMMON.scaledFallDamage.get()) {
				return distance * scale;
			}
		}

		return distance;
	}


	// todo: maybe these can cause a weird bug. Down below there is another todo, that can be the solution... but may cause a weird bug... idk
	//	@ModifyExpressionValue(method = "move", at = @At(value = "CONSTANT", ordinal = 0, args = "doubleValue=0.6D"))
	//	private double pehkui$move$flapping(double value)
	//	{
	//		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
	//
	//		if (scale != 1.0F)
	//		{
	//			return value / scale;
	//		}
	//
	//		return value;
	//	}
	//
	//	@ModifyExpressionValue(method = "move", at = @At(value = "CONSTANT", ordinal = 0, args = "floatValue=0.6F"))
	//	private float pehkui$move$bobbing(float value)
	//	{
	//		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
	//
	//		if (scale != 1.0F)
	//		{
	//			return value / scale;
	//		}
	//
	//		return value;
	//	}
	//
	//	@ModifyExpressionValue(method = "move", at = @At(value = "CONSTANT", ordinal = 1, args = "floatValue=0.6F"))
	//	private float pehkui$move$step(float value)
	//	{
	//		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
	//
	//		if (scale != 1.0F)
	//		{
	//			return value / scale;
	//		}
	//
	//		return value;
	//	}
// todo: you got to look at this.
// @ModifyVariable(
//    method = "move",
//    at = @At(value = "STORE"),
//    ordinal = 0
//)
//private float pehkui$scaleVelocityMultiplier(float value)
//{
//    float scale = ScaleUtils.getMotionScale((Entity)(Object)this);
//    return scale != 1.0F ? value / scale : value;
//}
}
