package virtuoel.pehkui.mixin.client.compat118minus.compat115plus;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ItemPickupParticle.class)
public class ItemPickupParticleMixin
{
	@Shadow @Final @Mutable Entity target;
	
	@ModifyExpressionValue(method = "buildGeometry", at = @At(value = "CONSTANT", args = "doubleValue=0.5D"))
	private double pehkui$buildGeometry$offset(double value, VertexConsumer vertexConsumer, Camera camera, float tickProgress)
	{
		final float scale = ScaleUtils.getEyeHeightScale(target, tickProgress);
		
		if (scale != 1.0F)
		{
			return value * scale;
		}
		
		return value;
	}
}
