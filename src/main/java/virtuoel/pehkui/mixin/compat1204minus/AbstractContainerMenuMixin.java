package virtuoel.pehkui.mixin.compat1204minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin
{
	@Dynamic
	@ModifyExpressionValue(method = "method_17696", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0))
	private static double pehkui$canUse$xOffset(double value, Block block, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockXOffset(pos, player);
	}
	
	@Dynamic
	@ModifyExpressionValue(method = "method_17696", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1))
	private static double pehkui$canUse$yOffset(double value, Block block, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockYOffset(pos, player);
	}
	
	@Dynamic
	@ModifyExpressionValue(method = "method_17696", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2))
	private static double pehkui$canUse$zOffset(double value, Block block, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockZOffset(pos, player);
	}
}
