package virtuoel.pehkui.mixin.client.compat1215plus;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

import java.util.List;

@Mixin(value = ItemRenderer.class, priority = 1010)
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
