package virtuoel.pehkui.mixin.client.compat115minus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(RemotePlayer.class)
public class RemotePlayerMixin
{
	@ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "floatValue=4.0F"))
	private float pehkui$tick$limbDistance(float value)
	{
		return ScaleUtils.modifyLimbDistance(value, (Entity) (Object) this);
	}
}
