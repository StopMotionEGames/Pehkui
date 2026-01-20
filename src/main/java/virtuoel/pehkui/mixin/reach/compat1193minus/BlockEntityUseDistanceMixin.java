package virtuoel.pehkui.mixin.reach.compat1193minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin({
	AbstractFurnaceBlockEntity.class,
	BrewingStandBlockEntity.class,
	RandomizableContainerBlockEntity.class,
})
public abstract class BlockEntityUseDistanceMixin
{
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.CAN_PLAYER_USE, at = @At(value = "CONSTANT", args = "doubleValue=64.0D"))
	private double pehkui$canPlayerUse$distance(double value, Player player)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		return scale != 1.0F ? scale * scale * value : value;
	}
}
