package virtuoel.pehkui.mixin.client.compat115plus;

import net.minecraft.client.render.entity.state.ItemFrameEntityRenderState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;

@Mixin(ItemFrameEntityRenderer.class)
public class ItemFrameEntityRendererMixin
{
	@ModifyVariable(method = "render(Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "STORE"))
	private Vec3d pehkui$render(Vec3d value, ItemFrameEntityRenderState state)
	{
		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;
		final float widthScale = ext.pehkui$getBoundingBoxWidthScale();
		final float heightScale = ext.pehkui$getBoundingBoxHeightScale();

		if (widthScale != 1.0F || heightScale != 1.0F)
		{
			value = value.multiply(1.0F / widthScale, 1.0F / heightScale, 1.0F / widthScale);

			final Direction facing = state.facing;

			final double widthOffset = ((0.0625D - (0.03125D * widthScale)) - 0.03125D) / widthScale;
			final double heightOffset = ((0.0625D - (0.03125D * heightScale)) - 0.03125D) / heightScale;

			value = value.add(widthOffset * facing.getOffsetX(), heightOffset * facing.getOffsetY(), widthOffset * facing.getOffsetZ());
		}

		return value;
	}
}
