package virtuoel.pehkui.mixin.compat1201minus;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.util.MixinConstants;

@Mixin(PlayerList.class)
public class PlayerListMixin
{
	@Dynamic
	@Inject(method = MixinConstants.ON_PLAYER_CONNECT, at = @At(value = "RETURN"))
	private void pehkui$onPlayerConnect(Connection connection, ServerPlayer player, CallbackInfo info)
	{
		for (ScaleType type : ScaleRegistries.SCALE_TYPES.values())
		{
			type.getScaleData(player).markForSync(true);
		}
	}
}
