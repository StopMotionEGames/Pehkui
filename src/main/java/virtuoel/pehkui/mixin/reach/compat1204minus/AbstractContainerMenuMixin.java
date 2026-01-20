package virtuoel.pehkui.mixin.reach.compat1204minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin
{
	@Dynamic
	@ModifyExpressionValue(method = "method_17696", at = @At(value = "CONSTANT", args = "doubleValue=64.0D"))
	private static double pehkui$canUse$distance(double value, Block block, Player player)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		return scale > 1.0F ? scale * scale * value : value;
	}
}
