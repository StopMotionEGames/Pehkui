package virtuoel.pehkui.mixin.compat1204minus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.HorseInventoryMenu;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(HorseInventoryMenu.class)
public class HorseInventoryMenuMixin
{
	@ModifyExpressionValue(method = "stillValid", at = @At(value = "CONSTANT", args = "floatValue=8.0F"))
	private float pehkui$canUse$distance(float value, @Local(argsOnly = true) Player player)
	{
		final float scale = ScaleUtils.getEntityReachScale(player);
		
		return scale != 1.0F ? scale * value : value;
	}
}
