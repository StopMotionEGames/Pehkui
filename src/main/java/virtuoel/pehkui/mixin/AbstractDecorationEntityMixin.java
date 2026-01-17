package virtuoel.pehkui.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractDecorationEntity.class)
public abstract class AbstractDecorationEntityMixin {
	@ModifyVariable(method = "dropStack", at = @At(value = "STORE"))
	private ItemEntity pehkui$dropStack(ItemEntity entity) {
		ScaleUtils.setScaleOfDrop(entity, (Entity) (Object) this);
		return entity;
	}

	@ModifyArg(method = "updateAttachmentPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/decoration/AbstractDecorationEntity;setBoundingBox(Lnet/minecraft/util/math/Box;)V"))
	private Box pehkui$updateAttachmentPosition$setBoundingBox(Box box) {
		final AbstractDecorationEntity entity = (AbstractDecorationEntity) (Object) this;

		final Direction facing = entity.getHorizontalFacing();

		final double xLength = box.getLengthX() / -2.0D;
		final double yLength = box.getLengthY() / -2.0D;
		final double zLength = box.getLengthZ() / -2.0D;

		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);

		if (widthScale != 1.0F || heightScale != 1.0F) {
			final double dX = xLength * (1.0D - widthScale);
			final double dY = yLength * (1.0D - heightScale);
			final double dZ = zLength * (1.0D - widthScale);
			box = box.expand(dX, dY, dZ);
			box = box.offset(dX * facing.getOffsetX(), dY * facing.getOffsetY(), dZ * facing.getOffsetZ());
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
