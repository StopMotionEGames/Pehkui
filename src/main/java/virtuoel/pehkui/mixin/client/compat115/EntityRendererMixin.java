package virtuoel.pehkui.mixin.client.compat115;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin
{
	@Dynamic
	@WrapOperation(method = MixinConstants.RENDER_LABEL_STRING_IF_PRESENT, at = @At(value = "INVOKE", target = MixinConstants.GET_HEIGHT))
	private float pehkui$renderLabelIfPresent$getHeight(Entity entity, Operation<Float> original)
	{
		final float delta = ScaleRenderUtils.getTickProgress(Minecraft.getInstance());
		return original.call(entity) / ScaleUtils.getBoundingBoxHeightScale(entity, delta);
	}
}
