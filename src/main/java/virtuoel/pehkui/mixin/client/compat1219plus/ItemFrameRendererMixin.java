package virtuoel.pehkui.mixin.client.compat1219plus;

import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;

@Mixin(ItemFrameRenderer.class)
public class ItemFrameRendererMixin
{
	@ModifyVariable(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "STORE"))
	private Vec3 pehkui$render(Vec3 value, ItemFrameRenderState state)
	{
		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;
		final float widthScale = ext.pehkui$getBoundingBoxWidthScale();
		final float heightScale = ext.pehkui$getBoundingBoxHeightScale();

		if (widthScale != 1.0F || heightScale != 1.0F)
		{
			value = value.multiply(1.0F / widthScale, 1.0F / heightScale, 1.0F / widthScale);

			final Direction facing = state.direction;

			final double widthOffset = ((0.0625D - (0.03125D * widthScale)) - 0.03125D) / widthScale;
			final double heightOffset = ((0.0625D - (0.03125D * heightScale)) - 0.03125D) / heightScale;

			value = value.add(widthOffset * facing.getStepX(), heightOffset * facing.getStepY(), widthOffset * facing.getStepZ());
		}

		return value;
	}
}
