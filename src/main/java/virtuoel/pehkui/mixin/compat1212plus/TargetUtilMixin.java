package virtuoel.pehkui.mixin.compat1212plus;

import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.TargetUtil;
import net.minecraft.item.ItemStack;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(TargetUtil.class)
public class TargetUtilMixin
{

	@Inject(method = "give(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;setToDefaultPickupDelay()V"))
	private static void pehkui$give(LivingEntity entity, ItemStack stack, Vec3d targetLocation, Vec3d velocityFactor, float yOffset, CallbackInfo ci, @Local ItemEntity itemEntity)
	{
		ScaleUtils.setScaleOfDrop(itemEntity, entity);
	}
	@ModifyExpressionValue(method = "give(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Vec3d;)V", at = @At(value = "CONSTANT", args = "floatValue=0.3F", ordinal = 0))
	private static float pehkui$give$offset(float value, LivingEntity entity, ItemStack stack, Vec3d targetLocation)
	{
		final float scale = ScaleUtils.getEyeHeightScale(entity);

		return scale != 1.0F ? scale * value : value;
	}

	@ModifyVariable(method = "give(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;F)V", at = @At(value = "STORE"))
	private static double pehkui$give$offset(double value, LivingEntity entity, ItemStack stack, Vec3d targetLocation, Vec3d velocityFactor, float yOffset)
	{
		final float scale = ScaleUtils.getEyeHeightScale(entity);

		return scale != 1.0F ? scale * value : value;
	}
}
