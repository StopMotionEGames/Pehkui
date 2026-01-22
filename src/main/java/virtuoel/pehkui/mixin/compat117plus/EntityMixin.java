package virtuoel.pehkui.mixin.compat117plus;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.api.PehkuiConfig;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Entity.class)
public class EntityMixin {
	@ModifyArg(method = "checkFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;F)V"))
	private float pehkui$fall$fallDistance(float distance) {
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
	}

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
