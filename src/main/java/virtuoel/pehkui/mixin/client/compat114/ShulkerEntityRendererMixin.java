package virtuoel.pehkui.mixin.client.compat114;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.math.Direction;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ShulkerEntityRenderer.class)
public class ShulkerEntityRendererMixin
{
	@Dynamic
	@Inject(at = @At("RETURN"), method = "setupTransforms(Lnet/minecraft/client/render/entity/state/ShulkerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;FF)V")
	private void pehkui$setupTransforms(LivingEntity entity, float animationProgress, float bodyYaw, float tickProgress, CallbackInfo info)
	{
		final Direction face = entity instanceof ShulkerEntity ? ((ShulkerEntity) entity).getAttachedFace() : Direction.DOWN;
		
		if (face != Direction.DOWN)
		{
			final float h = ScaleUtils.getModelHeightScale(entity, tickProgress);
			if (face != Direction.UP)
			{
				final float w = ScaleUtils.getModelWidthScale(entity, tickProgress);
				if (w != 1.0F || h != 1.0F)
				{
					GL11.glTranslated(0.0, -((1.0F - w) * 0.5F) / w, -((1.0F - h) * 0.5F) / h);
				}
			}
			else if (h != 1.0F)
			{
				GL11.glTranslated(0.0, -(1.0F - h) / h, 0.0);
			}
		}
	}
}
