package virtuoel.pehkui.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.renderer.entity.ItemRenderer;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin
{
//todo: see if item rendering does not break!

//	@Inject(method = "renderItem", at = @At(value = "HEAD"))
//	private void pehkui$renderItem$head(ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, int[] tints, List<BakedQuad> quads, RenderLayer layer, ItemRenderState.Glint glint, CallbackInfo ci)
//	{
// 		if (ScaleRenderUtils.shouldSkipHeadItemScaling(entity, stack, displayContext))
//		{
//			return;
//		}
//
//		ScaleRenderUtils.logIfItemRenderCancelled();
//
//		matrices.push();
//
//		if (!stack.isEmpty() && entity != null)
//		{
//			final float tickProgress = ScaleRenderUtils.getTickProgress(MinecraftClient.getInstance());
//			final float scale = ScaleUtils.getHeldItemScale(entity, tickProgress);
//
//			if (scale != 1.0F)
//			{
//				matrices.scale(scale, scale, scale);
//			}
//		}
//
//		matrices.push();
//
//		ScaleRenderUtils.saveLastRenderedItem(stack);
//	}
//
//	@Inject(method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V", at = @At(value = "RETURN"))
//	private void pehkui$renderItem$return(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int light, int overlay, int seed, CallbackInfo ci)
//	{
//		if (ScaleRenderUtils.shouldSkipHeadItemScaling(entity, stack, displayContext))
//		{
//			return;
//		}
//
//		ScaleRenderUtils.clearLastRenderedItem();
//
//		matrices.pop();
//		matrices.pop();
//	}
}
