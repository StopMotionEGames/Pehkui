package virtuoel.pehkui.mixin.compat1204minus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
	@Shadow ServerPlayer player;
	
	@ModifyExpressionValue(method = "handleUseItemOn", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0))
	private double pehkui$onPlayerInteractBlock$xOffset(double value, ServerboundUseItemOnPacket packet)
	{
		return ScaleUtils.getBlockXOffset(packet.getHitResult().getBlockPos(), player);
	}
	
	@ModifyExpressionValue(method = "handleUseItemOn", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1))
	private double pehkui$onPlayerInteractBlock$yOffset(double value, ServerboundUseItemOnPacket packet)
	{
		return ScaleUtils.getBlockYOffset(packet.getHitResult().getBlockPos(), player);
	}
	
	@ModifyExpressionValue(method = "handleUseItemOn", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2))
	private double pehkui$onPlayerInteractBlock$zOffset(double value, ServerboundUseItemOnPacket packet)
	{
		return ScaleUtils.getBlockZOffset(packet.getHitResult().getBlockPos(), player);
	}
}
