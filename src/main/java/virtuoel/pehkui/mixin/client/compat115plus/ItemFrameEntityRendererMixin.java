package virtuoel.pehkui.mixin.client.compat115plus;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.entity.ItemFrameEntityRenderer;

@Mixin(ItemFrameEntityRenderer.class)
public class ItemFrameEntityRendererMixin
{
//	@ModifyVariable(method = "render", at = @At(value = "STORE"))
//	private Vec3d pehkui$render(Vec3d value, ItemFrameEntity itemFrameEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i)
//	{
//		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(itemFrameEntity);
//		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(itemFrameEntity);
//
//		if (widthScale != 1.0F || heightScale != 1.0F)
//		{
//			value = value.multiply(1.0F / widthScale, 1.0F / heightScale, 1.0F / widthScale);
//
//			final Direction facing = itemFrameEntity.getHorizontalFacing();
//
//			final double widthOffset = ((0.0625D - (0.03125D * widthScale)) - 0.03125D) / widthScale;
//			final double heightOffset = ((0.0625D - (0.03125D * heightScale)) - 0.03125D) / heightScale;
//
//			value = value.add(widthOffset * facing.getOffsetX(), heightOffset * facing.getOffsetY(), widthOffset * facing.getOffsetZ());
//		}
//
//		return value;
//	}
	// todo: see this!
}
