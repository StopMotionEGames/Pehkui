package virtuoel.pehkui.mixin.client.renderer.entity;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HitboxRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin<S extends EntityRenderState> {
	@Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;DDDLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	private void pehkui$render$before(S entityRenderState, double d, double e, double f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, EntityRenderer<?, S> entityRenderer, CallbackInfo ci) {
		ScaleRenderUtils.logIfEntityRenderCancelled();

		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) entityRenderState;

		final float widthScale = ext.pehkui$getModelWidthScale();
		final float heightScale = ext.pehkui$getModelHeighScale();

		poseStack.pushPose();
		poseStack.scale(widthScale, heightScale, widthScale);
		poseStack.pushPose();

		ScaleRenderUtils.saveLastRenderedEntity(entityRenderState.entityType);
	}

	@Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;DDDLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(value = "INVOKE", shift = Shift.AFTER, target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	private void pehkui$render$after(S entityRenderState, double d, double e, double f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, EntityRenderer<?, S> entityRenderer, CallbackInfo ci) {
		ScaleRenderUtils.clearLastRenderedEntity();

		poseStack.popPose();
		poseStack.popPose();
	}

	@ModifyArg(method = "render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;DDDLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", index = 5, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FLnet/minecraft/world/level/LevelReader;F)V"))
	private float pehkui$render$shadowSize(float radius, @Local(argsOnly = true) EntityRenderState state) {
		return radius * ((PehkuiEntityRenderStateExtensions) state).pehkui$getModelWidthScale();
	}

//	@Inject(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShapeRenderer;renderLineBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDDDDFFFF)V", ordinal = 0))
//	private static void pehkui$renderHitbox(PoseStack poseStack, VertexConsumer vertexConsumer, HitboxRenderState hitboxRenderState, CallbackInfo ci) {
//		final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(entity);
//		final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(entity);
//		final float margin = entity.getPickRadius();
//
//		if (interactionWidth != 1.0F || interactionHeight != 1.0F || margin != 0.0F) {
//			AABB bounds = entity.getBoundingBox();
//
//			final double scaledXLength = bounds.getXsize() * 0.5D * (interactionWidth - 1.0F);
//			final double scaledYLength = bounds.getYsize() * 0.5D * (interactionHeight - 1.0F);
//			final double scaledZLength = bounds.getZsize() * 0.5D * (interactionWidth - 1.0F);
//			final double scaledMarginWidth = margin * interactionWidth;
//			final double scaledMarginHeight = margin * interactionHeight;
//
//			bounds = bounds.inflate(scaledXLength + scaledMarginWidth, scaledYLength + scaledMarginHeight, scaledZLength + scaledMarginWidth)
//				.move(-entity.getX(), -entity.getY(), -entity.getZ());
//
//			ScaleRenderUtils.renderInteractionBox(matrices, vertices, bounds);
//		}
//	}
}
