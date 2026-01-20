package virtuoel.pehkui.mixin.client.compat1206minus.compat115plus;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ReflectionUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(MobRenderer.class)
public class MobRendererMixin<T extends Mob>
{
	@Dynamic
	@Inject(method = MixinConstants.RENDER_LEASH, at = @At(value = "HEAD"))
	private <E extends Entity> void pehkui$renderLeash$head(T entity, float tickProgress, PoseStack matrices, MultiBufferSource provider, E holdingEntity, CallbackInfo info)
	{
		if (ReflectionUtils.getHoldingEntity(entity) != null)
		{
			final float inverseWidthScale = 1.0F / ScaleUtils.getModelWidthScale(entity, tickProgress);
			final float inverseHeightScale = 1.0F / ScaleUtils.getModelHeightScale(entity, tickProgress);
			
			matrices.pushPose();
			matrices.scale(inverseWidthScale, inverseHeightScale, inverseWidthScale);
			matrices.pushPose();
		}
	}
	
	@Dynamic
	@Inject(method = MixinConstants.RENDER_LEASH, at = @At(value = "RETURN"))
	private <E extends Entity> void pehkui$renderLeash$return(T entity, float tickProgress, PoseStack matrices, MultiBufferSource provider, E holdingEntity, CallbackInfo info)
	{
		if (ReflectionUtils.getHoldingEntity(entity) != null)
		{
			matrices.popPose();
			matrices.popPose();
		}
	}
}
