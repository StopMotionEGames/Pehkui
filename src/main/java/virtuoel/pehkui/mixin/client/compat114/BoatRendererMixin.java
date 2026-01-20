package virtuoel.pehkui.mixin.client.compat114;

import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.vehicle.boat.Boat;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(BoatRenderer.class)
public abstract class BoatRendererMixin
{
	@Dynamic
	@Inject(method = MixinConstants.RENDER_SECOND_PASS, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.RENDER_PASS))
	private void pehkui$renderSecondPass$before(Boat boatEntity, double x, double y, double z, float yaw, float tickProgress, CallbackInfo info)
	{
		final float widthScale = ScaleUtils.getModelWidthScale(boatEntity, tickProgress);
		final float heightScale = ScaleUtils.getModelHeightScale(boatEntity, tickProgress);
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.375F * (1.0F - heightScale), 0.0F);
		GL11.glPushMatrix();
		GL11.glScalef(widthScale, heightScale, widthScale);
		GL11.glPushMatrix();
	}
	
	@Dynamic
	@Inject(method = MixinConstants.RENDER_SECOND_PASS, at = @At(value = "INVOKE", shift = Shift.AFTER, target = MixinConstants.RENDER_PASS))
	private void pehkui$renderSecondPass$after(Boat boatEntity, double x, double y, double z, float yaw, float tickProgress, CallbackInfo info)
	{
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
