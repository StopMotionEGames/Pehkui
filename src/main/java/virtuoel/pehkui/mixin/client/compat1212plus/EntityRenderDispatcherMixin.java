package virtuoel.pehkui.mixin.client.compat1212plus;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityHitboxAndView;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	@Inject(
		method = "render(Lnet/minecraft/client/render/entity/state/EntityRenderState;DDDLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
	)
	private <S extends EntityRenderState> void pehkui$render$before(S state, double x, double y, double z, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<?, S> renderer, CallbackInfo ci) {
		ScaleRenderUtils.logIfEntityRenderCancelled();

		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;

		final float widthScale = ext.pehkui$getModelWidthScale();
		final float heightScale = ext.pehkui$getModelHeighScale();

		matrices.push();
		matrices.scale(widthScale, heightScale, widthScale);
		matrices.push();

		ScaleRenderUtils.saveLastRenderedEntity(state.entityType);
	}

	@Inject(
		method = "render(Lnet/minecraft/client/render/entity/state/EntityRenderState;DDDLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V",
		at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
	private <S extends EntityRenderState> void pehkui$render$after(S state, double x, double y, double z, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<?, S> renderer, CallbackInfo ci) {
		ScaleRenderUtils.clearLastRenderedEntity();

		matrices.pop();
		matrices.pop();
	}

	// todo: SEE IF SHADOW is OK!
	@ModifyArg(method = "render(Lnet/minecraft/client/render/entity/state/EntityRenderState;DDDLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V", index = 5, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;renderShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/EntityRenderState;FLnet/minecraft/world/WorldView;F)V"))
	private float pehkui$render$shadowSize(float radius, @Local(argsOnly = true) EntityRenderState state) {
		return radius * ((PehkuiEntityRenderStateExtensions) state).pehkui$getModelWidthScale();
	}

//	@Inject(method = "renderHitboxes(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/render/entity/state/EntityHitboxAndView;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexRendering;drawVector(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lorg/joml/Vector3f;Lnet/minecraft/util/math/Vec3d;I)V", ordinal = 0))
//	private static void pehkui$renderHitbox(MatrixStack matrices, EntityRenderState state, EntityHitboxAndView hitbox, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
//		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;
//		final float interactionWidth = ext.pehkui$getInteractionBoxWidthScale();
//		final float interactionHeight = ext.pehkui$getInteractionBoxHeightScale();
//		final float margin = ext.pehkui$getTargetingMargin();
//
//		if (interactionWidth != 1.0F || interactionHeight != 1.0F || margin != 0.0F) {
//			Box bounds = ext.pehkui$getCurrentBoundingBox();
//
//			final double scaledXLength = bounds.getLengthX() * 0.5D * (interactionWidth - 1.0F);
//			final double scaledYLength = bounds.getLengthY() * 0.5D * (interactionHeight - 1.0F);
//			final double scaledZLength = bounds.getLengthZ() * 0.5D * (interactionWidth - 1.0F);
//			final double scaledMarginWidth = margin * interactionWidth;
//			final double scaledMarginHeight = margin * interactionHeight;
//
//			bounds = bounds.expand(scaledXLength + scaledMarginWidth, scaledYLength + scaledMarginHeight, scaledZLength + scaledMarginWidth)
//				.offset(-state.x, -state.y, -state.z);
//
//			ScaleRenderUtils.renderInteractionBox(matrices, vertexConsumers.getBuffer(RenderLayer.getLines()), bounds);
//		}
//	}
}
