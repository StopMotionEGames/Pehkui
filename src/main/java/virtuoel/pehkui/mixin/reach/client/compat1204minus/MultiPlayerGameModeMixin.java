package virtuoel.pehkui.mixin.reach.client.compat1204minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin
{
	@Shadow @Final Minecraft minecraft;
	
	@Dynamic
	@ModifyReturnValue(method = MixinConstants.GET_REACH_DISTANCE, at = @At("RETURN"))
	private float pehkui$getReachDistance(float original)
	{
		if (minecraft.player != null)
		{
			final float scale = ScaleUtils.getBlockReachScale(minecraft.player);
			
			if (scale != 1.0F)
			{
				return original * scale;
			}
		}
		
		return original;
	}
}
