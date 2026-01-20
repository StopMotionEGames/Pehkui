package virtuoel.pehkui.mixin.client.compat1219plus;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleRenderUtils;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

	@Inject(
		method = "submit",
		at = @At(value = "HEAD")
	)
	private <S extends EntityRenderState> void pehkui$render$before(S renderState, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState, CallbackInfo ci) {
		ScaleRenderUtils.logIfEntityRenderCancelled();

		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) renderState;

		final float widthScale = ext.pehkui$getModelWidthScale();
		final float heightScale = ext.pehkui$getModelHeighScale();

		matrices.pushPose();
		matrices.scale(widthScale, heightScale, widthScale);
		matrices.pushPose();

		ScaleRenderUtils.saveLastRenderedEntity(renderState.entityType);
	}

	@Inject(
		method = "submit",
		at = @At(value = "RETURN"))
	private <S extends EntityRenderState> void pehkui$render$after(S renderState, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState, CallbackInfo ci) {
		ScaleRenderUtils.clearLastRenderedEntity();

		matrices.popPose();
		matrices.popPose();
	}

	// todo: SEE IF SHADOW is OK!
	@ModifyVariable(method = "extractShadow(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/level/Level;)V", at = @At(value = "STORE"))
	private float pehkui$render$radius(float radius, @Local(argsOnly = true) EntityRenderState state) {
		return radius * ((PehkuiEntityRenderStateExtensions) state).pehkui$getModelWidthScale();
	}

}
