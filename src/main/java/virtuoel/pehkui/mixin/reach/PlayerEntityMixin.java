package virtuoel.pehkui.mixin.reach;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerEntityMixin
{
	@ModifyExpressionValue(method = "attack", at = @At(value = "CONSTANT", args = "doubleValue=9.0F"))
	private double pehkui$attack$distance(double value)
	{
		final float scale = ScaleUtils.getEntityReachScale((Entity) (Object) this);
		
		return scale > 1.0F ? scale * scale * value : value;
	}
}
