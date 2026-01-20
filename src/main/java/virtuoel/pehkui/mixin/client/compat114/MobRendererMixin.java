package virtuoel.pehkui.mixin.client.compat114;

import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.world.entity.Mob;
import org.lwjgl.opengl.GL11;
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
	private void pehkui$renderLeash$head(T entity, double x, double y, double z, float yaw, float tickProgress, CallbackInfo info)
	{
		if (ReflectionUtils.getHoldingEntity(entity) != null)
		{
			final float inverseWidthScale = 1.0F / ScaleUtils.getModelWidthScale(entity, tickProgress);
			final float inverseHeightScale = 1.0F / ScaleUtils.getModelHeightScale(entity, tickProgress);
			
			GL11.glPushMatrix();
			GL11.glScalef(inverseWidthScale, inverseHeightScale, inverseWidthScale);
			GL11.glPushMatrix();
		}
	}
	
	@Dynamic
	@Inject(method = MixinConstants.RENDER_LEASH, at = @At(value = "RETURN"))
	private void pehkui$renderLeash$return(T entity, double x, double y, double z, float yaw, float tickProgress, CallbackInfo info)
	{
		if (ReflectionUtils.getHoldingEntity(entity) != null)
		{
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}
}
