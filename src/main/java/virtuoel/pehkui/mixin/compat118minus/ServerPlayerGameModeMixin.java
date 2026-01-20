package virtuoel.pehkui.mixin.compat118minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import virtuoel.pehkui.util.GravityChangerCompatibility;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin
{
	@Shadow ServerPlayer player;
	
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.PROCESS_BLOCK_BREAKING_ACTION, at = @At(value = "CONSTANT", args = "doubleValue=1.5D"))
	private double pehkui$processBlockBreakingAction$distance(double value)
	{
		return 0;
	}
	
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.PROCESS_BLOCK_BREAKING_ACTION, at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0))
	private double pehkui$processBlockBreakingAction$xOffset(double value, BlockPos pos, ServerboundPlayerActionPacket.Action action, Direction direction, int worldHeight)
	{
		return ScaleUtils.getBlockXOffset(pos, player) + GravityChangerCompatibility.INSTANCE.getXCorrection(player);
	}
	
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.PROCESS_BLOCK_BREAKING_ACTION, at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1))
	private double pehkui$processBlockBreakingAction$yOffset(double value, BlockPos pos, ServerboundPlayerActionPacket.Action action, Direction direction, int worldHeight)
	{
		return ScaleUtils.getBlockYOffset(pos, player) + GravityChangerCompatibility.INSTANCE.getYCorrection(player);
	}
	
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.PROCESS_BLOCK_BREAKING_ACTION, at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2))
	private double pehkui$processBlockBreakingAction$zOffset(double value, BlockPos pos, ServerboundPlayerActionPacket.Action action, Direction direction, int worldHeight)
	{
		return ScaleUtils.getBlockZOffset(pos, player) + GravityChangerCompatibility.INSTANCE.getZCorrection(player);
	}
}
