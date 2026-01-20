package virtuoel.pehkui.mixin.reach.client.compat1204minus;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(value = GameRenderer.class, priority = 990)
public class GameRendererMixin
{
	@Shadow @Final @Mutable
	Minecraft minecraft;
	
	@ModifyVariable(method = "pick", ordinal = 0, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/world/entity/Entity;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$updateCrosshairTarget$setDistance(double value, float tickProgress)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			final float scale = ScaleUtils.getEntityReachScale(entity, tickProgress);
			
			if (scale != 1.0F)
			{
				return scale * value;
			}
		}
		
		return value;
	}
	
	@ModifyVariable(method = "pick", ordinal = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$updateCrosshairTarget$squaredDistance(double value, float tickProgress)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			if (this.minecraft.hitResult == null || this.minecraft.hitResult.getType() == HitResult.Type.MISS)
			{
				final float scale = ScaleUtils.getEntityReachScale(entity, tickProgress);
				final double baseEntityReach = ScaleRenderUtils.hasExtendedReach(minecraft.gameMode) ? 6.0D : minecraft.gameMode.getPlayerMode().isCreative() ? 5.0F : 4.5F;
				final double entityReach = scale * baseEntityReach;
				
				return entityReach * entityReach;
			}
		}
		
		return value;
	}
	
	@ModifyExpressionValue(method = "pick", at = @At(value = "CONSTANT", args = "doubleValue=6.0D"))
	private double pehkui$updateCrosshairTarget$extendedDistance(double value, float tickProgress)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			final float scale = ScaleUtils.getEntityReachScale(entity, tickProgress);
			
			if (scale != 1.0F)
			{
				return scale * value;
			}
		}
		
		return value;
	}
	
	@ModifyExpressionValue(method = "pick", at = @At(value = "CONSTANT", args = "doubleValue=9.0D"))
	private double pehkui$updateCrosshairTarget$squaredMaxDistance(double value, float tickProgress)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			final float scale = ScaleUtils.getEntityReachScale(entity, tickProgress);
			
			if (scale != 1.0F)
			{
				return scale * scale * value;
			}
		}
		
		return value;
	}
}
