package virtuoel.pehkui.mixin.client.compat114;

import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Shulker;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ShulkerRenderer.class)
public class ShulkerRendererMixin
{
	@Dynamic
	@Inject(at = @At("RETURN"), method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/ShulkerRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V")
	private void pehkui$setupTransforms(LivingEntity entity, float animationProgress, float bodyYaw, float tickProgress, CallbackInfo info)
	{
		final Direction face = entity instanceof Shulker ? ((Shulker) entity).getAttachFace() : Direction.DOWN;
		
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
