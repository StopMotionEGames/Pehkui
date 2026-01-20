package virtuoel.pehkui.mixin.client.compat1212plus;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.state.ShulkerRenderState;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;

@Mixin(ShulkerRenderer.class)
public class ShulkerRendererMixin {
	@Inject(at = @At("RETURN"), method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/ShulkerRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V")
	private void pehkui$setupTransforms(ShulkerRenderState shulkerEntityRenderState, PoseStack matrixStack, float f, float g, CallbackInfo ci) {
		final Direction face = shulkerEntityRenderState.attachFace;
		if (face != Direction.DOWN) {
			final float h = ((PehkuiEntityRenderStateExtensions) shulkerEntityRenderState).pehkui$getModelHeighScale();
			if (face != Direction.UP) {
				final float w = ((PehkuiEntityRenderStateExtensions) shulkerEntityRenderState).pehkui$getModelWidthScale();
				if (w != 1.0F || h != 1.0F) {
					matrixStack.translate(0.0, -((1.0F - w) * 0.5F) / w, -((1.0F - h) * 0.5F) / h);
				}
			} else if (h != 1.0F) {
				matrixStack.translate(0.0, -(1.0F - h) / h, 0.0);
			}
		}
	}
}
