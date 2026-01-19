package virtuoel.pehkui.mixin.client.compat1219plus;

import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.util.math.MatrixStack;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleRenderUtils;

@Mixin(EntityRenderManager.class)
public class EntityRenderManagerMixin {

	@Inject(method = "render", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"))
	private <S extends EntityRenderState> void pehkui$render$before(S renderState, CameraRenderState cameraRenderState, double d, double e, double f, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CallbackInfo ci) {
		ScaleRenderUtils.logIfEntityRenderCancelled();

		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) renderState;

		final float widthScale = ext.pehkui$getModelWidthScale();
		final float heightScale = ext.pehkui$getModelHeighScale();

		matrixStack.push();
		matrixStack.scale(widthScale, heightScale, widthScale);
		matrixStack.push();

		ScaleRenderUtils.saveLastRenderedEntity(renderState.entityType);
	}

	@Inject(method = "render", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"))
	private <S extends EntityRenderState> void pehkui$render$after(S renderState, CameraRenderState cameraRenderState, double d, double e, double f, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CallbackInfo ci) {
		ScaleRenderUtils.clearLastRenderedEntity();

		matrixStack.pop();
		matrixStack.pop();
	}
}
