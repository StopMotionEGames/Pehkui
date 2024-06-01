package virtuoel.pehkui.mixin.reach.client.compat1204minus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import virtuoel.pehkui.util.MixinConstants;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
	@ModifyReturnValue(method = MixinConstants.GET_REACH_DISTANCE, at = @At("RETURN"))
	private float pehkui$getReachDistance(float original, @Local(ordinal = 0) float attrib)
	{
		if (original == attrib - 0.5F)
		{
			return attrib * 0.9F;
		}
		
		return original;
	}
}
