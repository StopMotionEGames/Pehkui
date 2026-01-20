package virtuoel.pehkui.mixin.client.compat115plus;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleRenderUtils;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
	@Inject(method = "checkPoseStack", at = @At(value = "HEAD"))
	private void pehkui$checkEmpty(PoseStack matrices, CallbackInfo info)
	{
		if (!matrices.isEmpty())
		{
			ScaleRenderUtils.logIfRenderCancelled();
		}
	}
}
