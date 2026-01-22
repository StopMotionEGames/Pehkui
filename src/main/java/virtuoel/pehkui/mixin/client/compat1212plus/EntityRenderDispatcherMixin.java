package virtuoel.pehkui.mixin.client.compat1212plus;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
	@Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	private <E extends Entity, S extends EntityRenderState> void pehkui$render$before(E entity, double x, double y, double z, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, EntityRenderer<? super E, S> renderer, CallbackInfo ci) {
		ScaleRenderUtils.logIfEntityRenderCancelled();

		final float widthScale = ScaleUtils.getModelWidthScale(entity, tickProgress);
		final float heightScale = ScaleUtils.getModelHeightScale(entity, tickProgress);

		matrices.pushPose();
		matrices.scale(widthScale, heightScale, widthScale);
		matrices.pushPose();

		ScaleRenderUtils.saveLastRenderedEntity(entity.getType());
	}

	@Inject(method = "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(value = "INVOKE", shift = Shift.AFTER, target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
	private <E extends Entity, S extends EntityRenderState> void pehkui$render$after(E entity, double x, double y, double z, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, EntityRenderer<? super E, S> renderer, CallbackInfo ci) {
		ScaleRenderUtils.clearLastRenderedEntity();

		matrices.popPose();
		matrices.popPose();
	}

	@ModifyArg(method = "render(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", index = 6, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;renderShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FFLnet/minecraft/world/level/LevelReader;F)V"))
	private float pehkui$render$shadowSize(float radius, @Local(argsOnly = true) Entity entity, @Local(argsOnly = true) float tickProgress) {
		return radius * ScaleUtils.getModelWidthScale(entity, tickProgress);
	}

	@Inject(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ShapeRenderer;renderLineBox(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDDDDFFFF)V", ordinal = 0))
	private static void pehkui$renderHitbox(PoseStack matrices, VertexConsumer vertices, Entity entity, float tickProgress, float red, float green, float blue, CallbackInfo ci) {
		final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(entity);
		final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(entity);
		final float margin = entity.getPickRadius();

		if (interactionWidth != 1.0F || interactionHeight != 1.0F || margin != 0.0F) {
			AABB bounds = entity.getBoundingBox();

			final double scaledXLength = bounds.getXsize() * 0.5D * (interactionWidth - 1.0F);
			final double scaledYLength = bounds.getYsize() * 0.5D * (interactionHeight - 1.0F);
			final double scaledZLength = bounds.getZsize() * 0.5D * (interactionWidth - 1.0F);
			final double scaledMarginWidth = margin * interactionWidth;
			final double scaledMarginHeight = margin * interactionHeight;

			bounds = bounds.inflate(scaledXLength + scaledMarginWidth, scaledYLength + scaledMarginHeight, scaledZLength + scaledMarginWidth)
				.move(-entity.getX(), -entity.getY(), -entity.getZ());

			ScaleRenderUtils.renderInteractionBox(matrices, vertices, bounds);
		}
	}
}
