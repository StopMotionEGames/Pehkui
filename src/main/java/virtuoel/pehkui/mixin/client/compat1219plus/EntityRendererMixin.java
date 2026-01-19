package virtuoel.pehkui.mixin.client.compat1219plus;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.util.math.MatrixStack;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleRenderUtils;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

	@Inject(
		method = "render",
		at = @At(value = "HEAD")
	)
	private <S extends EntityRenderState> void pehkui$render$before(S renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState, CallbackInfo ci) {
		ScaleRenderUtils.logIfEntityRenderCancelled();

		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) renderState;

		final float widthScale = ext.pehkui$getModelWidthScale();
		final float heightScale = ext.pehkui$getModelHeighScale();

		matrices.push();
		matrices.scale(widthScale, heightScale, widthScale);
		matrices.push();

		ScaleRenderUtils.saveLastRenderedEntity(renderState.entityType);
	}

	@Inject(
		method = "render",
		at = @At(value = "RETURN"))
	private <S extends EntityRenderState> void pehkui$render$after(S renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState, CallbackInfo ci) {
		ScaleRenderUtils.clearLastRenderedEntity();

		matrices.pop();
		matrices.pop();
	}

	// todo: SEE IF SHADOW is OK!
	@ModifyVariable(method = "updateShadow(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/world/World;)V", at = @At(value = "STORE"))
	private float pehkui$render$radius(float radius, @Local(argsOnly = true) EntityRenderState state) {
		return radius * ((PehkuiEntityRenderStateExtensions) state).pehkui$getModelWidthScale();
	}

}
