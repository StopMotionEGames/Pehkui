package virtuoel.pehkui.mixin.client.renderer.entity;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState>
{
	@Inject(
		method = "submit",
		at = @At(value = "HEAD")
	)
	private void pehkui$submit$before(S renderState, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState, CallbackInfo ci) {
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
	private void pehkui$submit$after(S renderState, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState, CallbackInfo ci) {
		ScaleRenderUtils.clearLastRenderedEntity();

		matrices.popPose();
		matrices.popPose();
	}

	// todo: SEE IF SHADOW is OK!
	@ModifyVariable(method = "extractShadow(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/level/Level;)V", at = @At(value = "STORE"))
	private float pehkui$extractShadow$radius(float radius, @Local(argsOnly = true) EntityRenderState state) {
		return radius * ((PehkuiEntityRenderStateExtensions) state).pehkui$getModelWidthScale();
	}

	@Inject(method = "extractRenderState", at=@At("RETURN"))
	private void pehkui$copyScale(T entity, S state, float tickProgress, CallbackInfo ci){
		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;

		ext.pehkui$setModelWidthScale(ScaleUtils.getModelWidthScale(entity, tickProgress));
		ext.pehkui$setModelHeightScale(ScaleUtils.getModelHeightScale(entity, tickProgress));
		ext.pehkui$setBoundingBoxWidthScale(ScaleUtils.getBoundingBoxWidthScale(entity, tickProgress));
		ext.pehkui$setBoundingBoxHeightScale(ScaleUtils.getBoundingBoxHeightScale(entity, tickProgress));
		ext.pehkui$setInteractionBoxWidthScale(ScaleUtils.getBoundingBoxWidthScale(entity,tickProgress));
		ext.pehkui$setInteractionBoxHeightScale(ScaleUtils.getBoundingBoxHeightScale(entity,tickProgress));
		ext.pehkui$setTargetingMargin(entity.getPickRadius());
		ext.pehkui$setCurrentBoundingBox(entity.getBoundingBox());
	}
//	@Inject(method = "renderLeash", at = @At(value = "HEAD"))
//	private <E extends Entity> void pehkui$renderLeash$head(MatrixStack matrices, VertexConsumerProvider vertexConsumers, EntityRenderState.LeashData leashData, CallbackInfo ci)
//	{
//		final float widthScale = ScaleUtils.getModelWidthScale(entity, tickProgress);
//		final float heightScale = ScaleUtils.getModelHeightScale(entity, tickProgress);
//
//		final float inverseWidthScale = 1.0F / widthScale;
//		final float inverseHeightScale = 1.0F / heightScale;
//
//		matrices.push();
//		matrices.scale(inverseWidthScale, inverseHeightScale, inverseWidthScale);
//		matrices.push();
//	}
	
//	@Inject(method = "renderLeash", at = @At(value = "RETURN"))
//	private static <E extends Entity> void pehkui$renderLeash$return(MatrixStack matrices, VertexConsumerProvider vertexConsumers, EntityRenderState.LeashData leashData, CallbackInfo ci)
//	{
//		matrices.pop();
//		matrices.pop();
//	}
//	todo: See this!
//	@ModifyExpressionValue(method = "renderLabelIfPresent", at = @At(value = "CONSTANT", args = "doubleValue=0.5D"))
//	private <S extends EntityRenderState> double pehkui$renderLabelIfPresent$offset(double original, @Local(argsOnly = true) S state)
//	{
//		final float scale = ScaleUtils.getBoundingBoxHeightScale(entity);
//
//		return scale != 1.0F ? value + (entity.getHeight() * ((1.0F / scale) - 1.0F)) : value;
//	}
}
