package virtuoel.pehkui.mixin.compat118minus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
	@WrapOperation(method = "handleInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;squaredDistanceTo(Lnet/minecraft/world/entity/Entity;)D"))
	private double pehkui$onPlayerInteractEntity$squaredDistanceTo(ServerPlayer player, Entity entity, Operation<Double> original)
	{
		final Vec3 eyePos = ScaleUtils.getEyePos(player);
		final float margin = entity.getPickRadius();
		
		AABB box = entity.getBoundingBox().inflate(margin);
		
		final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(entity);
		final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(entity);
		
		if (interactionWidth != 1.0F || interactionHeight != 1.0F)
		{
			final double scaledXLength = box.getXsize() * 0.5D * (interactionWidth - 1.0F);
			final double scaledYLength = box.getYsize() * 0.5D * (interactionHeight - 1.0F);
			final double scaledZLength = box.getZsize() * 0.5D * (interactionWidth - 1.0F);
			final double scaledMarginWidth = margin * (interactionWidth - 1.0F);
			final double scaledMarginHeight = margin * (interactionHeight - 1.0F);
			
			box = box.inflate(scaledXLength + scaledMarginWidth, scaledYLength + scaledMarginHeight, scaledZLength + scaledMarginWidth);
		}
		
		final double nearestX;
		if (eyePos.x < box.minX)
		{
			nearestX = box.minX;
		}
		else if (eyePos.x > box.maxX)
		{
			nearestX = box.maxX;
		}
		else
		{
			nearestX = eyePos.x;
		}
		
		final double nearestY;
		if (eyePos.y < box.minY)
		{
			nearestY = box.minY;
		}
		else nearestY = Math.min(eyePos.y, box.maxY);
		
		final double nearestZ;
		if (eyePos.z < box.minZ)
		{
			nearestZ = box.minZ;
		}
		else nearestZ = Math.min(eyePos.z, box.maxZ);
		
		return eyePos.distanceToSqr(nearestX, nearestY, nearestZ);
	}
}
