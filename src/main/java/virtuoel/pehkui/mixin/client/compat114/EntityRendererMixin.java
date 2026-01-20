package virtuoel.pehkui.mixin.client.compat114;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
	@Dynamic
	@Shadow
	float shadowRadius; // UNMAPPED_FIELD

	@Dynamic
	@WrapOperation(method = "submitNameTag", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;width(Lnet/minecraft/network/chat/FormattedText;)I"))
	private float pehkui$renderLabel$getHeight(Entity entity, Operation<Float> original) {
		final float delta = ScaleRenderUtils.getTickProgress(Minecraft.getInstance());
		return original.call(entity) / ScaleUtils.getBoundingBoxHeightScale(entity, delta);
	}
// todo: found the correct way to use this method.
//	@Dynamic
//	@WrapOperation(method = MixinConstants.POST_RENDER, at = @At(value = "INVOKE", target = MixinConstants.RENDER_SHADOW))
//	private void pehkui$postRender$renderShadow(EntityRenderer<Entity> obj, Entity entity, double x, double y, double z, float opacity, float tickProgress, Operation<Void> original)
//	{
//		final float scale = ScaleUtils.getModelWidthScale(entity, tickProgress);
//
//		if (scale != 1.0F)
//		{
//			final float temp = shadowRadius;
//
//			shadowRadius *= scale;
//
//			GL11.glPushMatrix();
//			GL11.glTranslated(0, -0.0155, 0);
//			GL11.glPushMatrix();
//
//			original.call(obj, entity, x, y, z, opacity, tickProgress);
//
//			GL11.glPopMatrix();
//			GL11.glPopMatrix();
//
//			shadowRadius = temp;
//		}
//		else
//		{
//			original.call(obj, entity, x, y, z, opacity, tickProgress);
//		}
//	}
}
