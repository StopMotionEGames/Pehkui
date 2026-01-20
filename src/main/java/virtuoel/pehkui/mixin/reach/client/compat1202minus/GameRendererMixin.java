package virtuoel.pehkui.mixin.reach.client.compat1202minus;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(value = GameRenderer.class, priority = 990)
public class GameRendererMixin
{
	@Shadow @Final @Mutable
	Minecraft minecraft;
	
	@ModifyExpressionValue(method = "pick", at = @At(value = "CONSTANT", args = "doubleValue=3.0D"))
	private double pehkui$updateCrosshairTarget$distance(double value, float tickProgress)
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
}
