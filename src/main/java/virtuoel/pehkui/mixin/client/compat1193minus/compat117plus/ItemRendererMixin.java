package virtuoel.pehkui.mixin.client.compat1193minus.compat117plus;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(value = ItemRenderer.class, priority = 1010)
public class ItemRendererMixin
{
	@Dynamic
	@Inject(method = MixinConstants.RENDER_ITEM_WITH_SEED, at = @At(value = "HEAD"))
	private void pehkui$renderItem$head(@Nullable LivingEntity entity, ItemStack item, @Coerce Object renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, @Nullable Level world, int light, int overlay, int seed, CallbackInfo info)
	{
		if (ScaleRenderUtils.shouldSkipHeadItemScaling(entity, item, renderMode))
		{
			return;
		}
		
		ScaleRenderUtils.logIfItemRenderCancelled();
		
		matrices.pushPose();
		
		if (!item.isEmpty() && entity != null)
		{
			final float tickProgress = ScaleRenderUtils.getTickProgress(Minecraft.getInstance());
			final float scale = ScaleUtils.getHeldItemScale(entity, tickProgress);
			
			if (scale != 1.0F)
			{
				matrices.scale(scale, scale, scale);
			}
		}
		
		matrices.pushPose();
		
		ScaleRenderUtils.saveLastRenderedItem(item);
	}
	
	@Dynamic
	@Inject(method = MixinConstants.RENDER_ITEM_WITH_SEED, at = @At(value = "RETURN"))
	private void pehkui$renderItem$return(@Nullable LivingEntity entity, ItemStack item, @Coerce Object renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, @Nullable Level world, int light, int overlay, int seed, CallbackInfo info)
	{
		if (ScaleRenderUtils.shouldSkipHeadItemScaling(entity, item, renderMode))
		{
			return;
		}
		
		ScaleRenderUtils.clearLastRenderedItem();
		
		matrices.popPose();
		matrices.popPose();
	}
}
