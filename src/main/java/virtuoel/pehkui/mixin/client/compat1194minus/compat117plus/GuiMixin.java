package virtuoel.pehkui.mixin.client.compat1194minus.compat117plus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Gui.class)
public abstract class GuiMixin
{
	@Shadow
	protected abstract Player getCameraPlayer();
	
	@Shadow @Final @Mutable
	private Minecraft minecraft;
	
	@Dynamic
	@ModifyArg(method = MixinConstants.RENDER_STATUS_BARS, index = 0, at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F"))
	private float pehkui$renderStatusBars(float value)
	{
		final float healthScale = ScaleUtils.getHealthScale(getCameraPlayer(), ScaleRenderUtils.getTickProgress(minecraft));
		
		return healthScale != 1.0F ? value * healthScale : value;
	}
}
