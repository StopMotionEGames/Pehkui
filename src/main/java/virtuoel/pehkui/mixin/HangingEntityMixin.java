package virtuoel.pehkui.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(HangingEntity.class)
public abstract class HangingEntityMixin {
	@ModifyVariable(method = "spawnAtLocation", at = @At(value = "STORE"))
	private ItemEntity pehkui$dropStack(ItemEntity entity) {
		ScaleUtils.setScaleOfDrop(entity, (Entity) (Object) this);
		return entity;
	}

	@ModifyArg(method = "recalculateBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/HangingEntity;setBoundingBox(Lnet/minecraft/world/phys/AABB;)V"))
	private AABB pehkui$updateAttachmentPosition$setBoundingBox(AABB box) {
		final HangingEntity entity = (HangingEntity) (Object) this;

		final Direction facing = entity.getDirection();

		final double xLength = box.getXsize() / -2.0D;
		final double yLength = box.getYsize() / -2.0D;
		final double zLength = box.getZsize() / -2.0D;

		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);

		if (widthScale != 1.0F || heightScale != 1.0F) {
			final double dX = xLength * (1.0D - widthScale);
			final double dY = yLength * (1.0D - heightScale);
			final double dZ = zLength * (1.0D - widthScale);
			box = box.inflate(dX, dY, dZ);
			box = box.move(dX * facing.getStepX(), dY * facing.getStepY(), dZ * facing.getStepZ());
		}

		return box;
	}

//	@ModifyArg(method = "updateAttachmentPosition", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/AbstractDecorationEntity;setPos(DDD)V"))
//	private double pehkui$updateAttachmentPosition$setPos$x(double x, @Local Box box) {
//		final AbstractDecorationEntity entity = (AbstractDecorationEntity) (Object) this;
//
//		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
//		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
//
//		final Direction facing = entity.getHorizontalFacing();
//		if (widthScale != 1.0F || heightScale != 1.0F) {
//			final double widthOffset = ((0.0625D - (0.03125D * widthScale)) - 0.03125D) / widthScale;
//
//			return x * widthOffset;
////				widthOffset * facing.getOffsetX(),
//
//		}
//		return x;
//	}
//
//	@ModifyArg(method = "updateAttachmentPosition", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/AbstractDecorationEntity;setPos(DDD)V"))
//	private double pehkui$updateAttachmentPosition$setPos$y(double y, @Local Box box) {
//		final AbstractDecorationEntity entity = (AbstractDecorationEntity) (Object) this;
//
//		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
//		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
//
//		final Direction facing = entity.getHorizontalFacing();
//
//		if (widthScale != 1.0F || heightScale != 1.0F) {
//
//			return y - 1.0D / heightScale * facing.getOffsetY();
//		}
//		return y;
//	}
//
//	@ModifyArg(method = "updateAttachmentPosition", index = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/AbstractDecorationEntity;setPos(DDD)V"))
//	private double pehkui$updateAttachmentPosition$setPos$z(double z, @Local Box box) {
//		final AbstractDecorationEntity entity = (AbstractDecorationEntity) (Object) this;
//
//		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
//		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
//
//		final Direction facing = entity.getHorizontalFacing();
//
//		if (widthScale != 1.0F || heightScale != 1.0F) {
//
//			final double widthOffset = ((0.0625D - (0.03125D * widthScale)) - 0.03125D) / widthScale;
//
//			return facing.getOffsetZ() * widthOffset;
////				widthOffset * facing.getOffsetZ()
//		}
//		return z;
//	}
}
