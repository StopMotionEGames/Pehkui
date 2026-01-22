package virtuoel.pehkui.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
	@Inject(method = "extractRenderState", at = @At("RETURN"))
	private void pehkui$copyScale(T entity, S state, float tickDelta, CallbackInfo ci) {
		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;

		ext.pehkui$setModelWidthScale(ScaleUtils.getModelWidthScale(entity, tickDelta));
		ext.pehkui$setModelHeightScale(ScaleUtils.getModelHeightScale(entity, tickDelta));
		ext.pehkui$setBoundingBoxWidthScale(ScaleUtils.getBoundingBoxWidthScale(entity, tickDelta));
		ext.pehkui$setBoundingBoxHeightScale(ScaleUtils.getBoundingBoxHeightScale(entity, tickDelta));
	}

	//	@Inject(method = "renderLeash", at = @At(value = "HEAD"))
//	private <E extends Entity> void pehkui$renderLeash$head(MatrixStack matrices, VertexConsumerProvider vertexConsumers, EntityRenderState.LeashData leashData, CallbackInfo ci)
//	{
//		final float widthScale = ScaleUtils.getModelWidthScale(entity, tickDelta);
//		final float heightScale = ScaleUtils.getModelHeightScale(entity, tickDelta);
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
