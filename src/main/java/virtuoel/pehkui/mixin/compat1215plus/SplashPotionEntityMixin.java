package virtuoel.pehkui.mixin.compat1215plus;

import net.minecraft.entity.projectile.thrown.SplashPotionEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(SplashPotionEntity.class)
public class SplashPotionEntityMixin
{
	@WrapOperation(method = "spawnAreaEffectCloud", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Box;expand(DDD)Lnet/minecraft/util/math/Box;"))
	private Box pehkui$applySplashPotion$expand(Box obj, double x, double y, double z, Operation<Box> original)
	{
		Entity entity = (Entity) (Object) this;
		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
		
		if (widthScale != 1.0F)
		{
			x *= widthScale;
			z *= widthScale;
		}
		
		if (heightScale != 1.0F)
		{
			y *= heightScale;
		}
		
		return original.call(obj, x, y, z);
	}
	
	@ModifyExpressionValue(method = "spawnAreaEffectCloud", at = @At(value = "CONSTANT", args = "doubleValue=16.0D"))
	private double pehkui$applySplashPotion$maxSquaredDist(double value)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * scale * value : value;
	}
	
	@ModifyExpressionValue(method = "spawnAreaEffectCloud", at = @At(value = "CONSTANT", args = "doubleValue=4.0F", ordinal = 2))
	private double pehkui$applySplashPotion$maxDist(double value)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
