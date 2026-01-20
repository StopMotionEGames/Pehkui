package virtuoel.pehkui.mixin.compat1194plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerMixin
{
	@ModifyReturnValue(method = "getFlyingSpeed", at = @At(value = "RETURN", ordinal = 0))
	private float pehkui$getOffGroundSpeed(float original)
	{
		final float scale = ScaleUtils.getFlightScale((Player) (Object) this);
		
		return scale != 1.0F ? original * scale : original;
	}
}
