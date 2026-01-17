package virtuoel.pehkui.mixin.client.compat1212plus;

import net.minecraft.client.render.entity.state.ShulkerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;

@Mixin(ShulkerEntityRenderer.class)
public class ShulkerEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "setupTransforms(Lnet/minecraft/client/render/entity/state/ShulkerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;FF)V")
	private void pehkui$setupTransforms(ShulkerEntityRenderState shulkerEntityRenderState, MatrixStack matrixStack, float f, float g, CallbackInfo ci) {
		final Direction face = shulkerEntityRenderState.facing;
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
