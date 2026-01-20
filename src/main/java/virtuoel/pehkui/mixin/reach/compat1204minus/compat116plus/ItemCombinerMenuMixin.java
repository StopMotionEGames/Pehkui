package virtuoel.pehkui.mixin.reach.compat1204minus.compat116plus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ItemCombinerMenu.class)
public class ItemCombinerMenuMixin
{
	@Dynamic
	@ModifyExpressionValue(method = "method_24924", at = @At(value = "CONSTANT", args = "doubleValue=64.0D"))
	private double pehkui$canUse$distance(double value, Player player)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		return scale != 1.0F ? scale * scale * value : value;
	}
}
