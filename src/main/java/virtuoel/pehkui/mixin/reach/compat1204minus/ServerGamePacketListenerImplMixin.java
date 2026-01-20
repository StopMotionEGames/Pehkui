package virtuoel.pehkui.mixin.reach.compat1204minus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
	@Shadow ServerPlayer player;
	
	@ModifyExpressionValue(method = "handleUseItemOn", at = @At(value = "CONSTANT", args = "doubleValue=64.0D"))
	private double pehkui$onPlayerInteractBlock$distance(double value)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		
		return scale != 1.0F ? scale * scale * value : value;
	}
}
