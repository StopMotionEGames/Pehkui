package virtuoel.pehkui.mixin.compat1212plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(BehaviorUtils.class)
public class TargetUtilMixin
{

	@Inject(method = "throwItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;setDefaultPickUpDelay()V"))
	private static void pehkui$give(LivingEntity entity, ItemStack stack, Vec3 targetLocation, Vec3 velocityFactor, float yOffset, CallbackInfo ci, @Local ItemEntity itemEntity)
	{
		ScaleUtils.setScaleOfDrop(itemEntity, entity);
	}
	@ModifyExpressionValue(method = "throwItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "CONSTANT", args = "floatValue=0.3F", ordinal = 0))
	private static float pehkui$give$offset(float value, LivingEntity entity, ItemStack stack, Vec3 targetLocation)
	{
		final float scale = ScaleUtils.getEyeHeightScale(entity);

		return scale != 1.0F ? scale * value : value;
	}

	@ModifyVariable(method = "throwItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;F)V", at = @At(value = "STORE"))
	private static double pehkui$give$offset(double value, LivingEntity entity, ItemStack stack, Vec3 targetLocation, Vec3 velocityFactor, float yOffset)
	{
		final float scale = ScaleUtils.getEyeHeightScale(entity);

		return scale != 1.0F ? scale * value : value;
	}
}
