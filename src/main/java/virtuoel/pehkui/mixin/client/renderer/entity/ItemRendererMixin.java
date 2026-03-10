package virtuoel.pehkui.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
// //todo: see if item rendering does not break!
//	@Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", at = @At(value = "HEAD"))
//	private void pehkui$renderItem$head(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, PoseStack matrices, MultiBufferSource vertexConsumers, Level world, int light, int overlay, int seed, CallbackInfo ci)
//	{
//		if (ScaleRenderUtils.shouldSkipHeadItemScaling(entity, stack, displayContext))
//		{
//			return;
//		}
//
//		ScaleRenderUtils.logIfItemRenderCancelled();
//
//		matrices.pushPose();
//
//		if (!stack.isEmpty() && entity != null)
//		{
//			final float tickDelta = ScaleRenderUtils.getTickDelta(Minecraft.getInstance());
//			final float scale = ScaleUtils.getHeldItemScale(entity, tickDelta);
//
//			if (scale != 1.0F)
//			{
//				matrices.scale(scale, scale, scale);
//			}
//		}
//
//		matrices.pushPose();
//
//		ScaleRenderUtils.saveLastRenderedItem(stack);
//	}
//
//	@Inject(method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", at = @At(value = "RETURN"))
//	private void pehkui$renderItem$return(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, PoseStack matrices, MultiBufferSource vertexConsumers, Level world, int light, int overlay, int seed, CallbackInfo ci)
//	{
//		if (ScaleRenderUtils.shouldSkipHeadItemScaling(entity, stack, displayContext))
//		{
//			return;
//		}
//
//		ScaleRenderUtils.clearLastRenderedItem();
//
//		matrices.popPose();
//		matrices.popPose();
//	}
}
