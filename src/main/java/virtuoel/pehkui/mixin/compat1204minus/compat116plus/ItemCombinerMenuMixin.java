package virtuoel.pehkui.mixin.compat1204minus.compat116plus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.level.Level;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ItemCombinerMenu.class)
public class ItemCombinerMenuMixin
{
	@Dynamic
	@ModifyExpressionValue(method = "method_24924", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 0))
	private double pehkui$canUse$xOffset(double value, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockXOffset(pos, player);
	}
	
	@Dynamic
	@ModifyExpressionValue(method = "method_24924", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 1))
	private double pehkui$canUse$yOffset(double value, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockYOffset(pos, player);
	}
	
	@Dynamic
	@ModifyExpressionValue(method = "method_24924", at = @At(value = "CONSTANT", args = "doubleValue=0.5D", ordinal = 2))
	private double pehkui$canUse$zOffset(double value, Player player, Level world, BlockPos pos)
	{
		return ScaleUtils.getBlockZOffset(pos, player);
	}
}
