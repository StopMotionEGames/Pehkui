package virtuoel.pehkui.mixin.compat117plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerEntityMixin
{
	@ModifyExpressionValue(method = "attack(Lnet/minecraft/world/entity/Entity;)V", at = @At(value = "CONSTANT", args = "doubleValue=0.4000000059604645D"))
	private double pehkui$attack$knockback(double value)
	{
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
