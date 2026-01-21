package virtuoel.pehkui.mixin.client;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin
{
	@ModifyExpressionValue(method = "aiStep", at = @At(value = "CONSTANT", args = "floatValue=3.0F"))
	private float pehkui$aiStep$flightSpeed(float value)
	{
		final float scale = ScaleUtils.getFlightScale((Entity) (Object) this);

		return scale != 1.0F ? scale * value : value;
	}

	@ModifyExpressionValue(method = "updateAutoJump", at = { @At(value = "CONSTANT", args = "floatValue=1.2F"), @At(value = "CONSTANT", args = "floatValue=0.75F") })
	private float pehkui$updateAutoJump$heightAndBoost(float value)
	{
		Player player = (Player) (Object) this;
		final float scale = ScaleUtils.getMotionScale(player);
		final float jumpScale = ScaleUtils.getJumpHeightScale(player);

		return scale != 1.0F || jumpScale != 1.0F ? scale * jumpScale * value : value;
	}
}
