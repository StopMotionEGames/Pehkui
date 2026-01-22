package virtuoel.pehkui.mixin.world.entity.monster;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.phys.AABB;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Shulker.class)
public class ShulkerMixin {
	@ModifyReturnValue(method = "makeBoundingBox", at = @At("RETURN"))
	private AABB pehkui$calculateBoundingBox(AABB originalBox) {
		final Shulker entity = (Shulker) (Object) this;

		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
		if (widthScale == 1.0F && heightScale == 1.0F) return originalBox;

		BlockPos pos = entity.blockPosition();
		Direction facing = entity.getAttachFace().getOpposite();


		double openProgress = entity.getClientPeekAmount(ScaleRenderUtils.getTickDelta(Minecraft.getInstance()));

		double scaledWidth = 1.0D * widthScale;
		double scaledHeight = (1.0D + (openProgress * 0.5D)) * heightScale;

		double cX = pos.getX() + 0.5D;
		double cY = pos.getY() + 0.5D;
		double cZ = pos.getZ() + 0.5D;

		double w = scaledWidth / 2.0D;

		double minX = cX - w, maxX = cX + w;
		double minY = cY - w, maxY = cY + w;
		double minZ = cZ - w, maxZ = cZ + w;

		switch (facing) {
			case UP -> {
				minY = pos.getY();
				maxY = minY + scaledHeight;
			}
			case DOWN -> {
				maxY = pos.getY() + 1.0D;
				minY = maxY - scaledHeight;
			}
			case SOUTH -> {
				minZ = pos.getZ();
				maxZ = minZ + scaledHeight;
			}
			case NORTH -> {
				maxZ = pos.getZ() + 1.0D;
				minZ = maxZ - scaledHeight;
			}
			case EAST -> {
				minX = pos.getX();
				maxX = minX + scaledHeight;
			}
			case WEST -> {
				maxX = pos.getX() + 1.0D;
				minX = maxX - scaledHeight;
			}
		}

		return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
