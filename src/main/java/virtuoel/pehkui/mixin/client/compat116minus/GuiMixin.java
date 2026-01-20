package virtuoel.pehkui.mixin.client.compat116minus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Gui.class)
public abstract class GuiMixin
{
	@Dynamic
	@ModifyVariable(method = MixinConstants.RENDER_STATUS_BARS, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.GET_ABSORPTION_AMOUNT))
	private float pehkui$renderStatusBars(float value)
	{
		final Minecraft client = Minecraft.getInstance();
		
		final float healthScale = ScaleUtils.getHealthScale(client.getCameraEntity(), ScaleRenderUtils.getTickProgress(client));
		
		return healthScale != 1.0F ? value * healthScale : value;
	}
}
