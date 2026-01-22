package virtuoel.pehkui.mixin.compat1205plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public class PlayerEntityMixin
{
	@ModifyReturnValue(method = "blockInteractionRange", at = @At("RETURN"))
	private double pehkui$getBlockInteractionRange(double original)
	{
		final float scale = ScaleUtils.getBlockReachScale((Entity) (Object) this);
		return scale != 1.0F ? scale * original : original;
	}
	
	@ModifyReturnValue(method = "entityInteractionRange", at = @At("RETURN"))
	private double pehkui$getEntityInteractionRange(double original)
	{
		final float scale = ScaleUtils.getEntityReachScale((Entity) (Object) this);
		return scale != 1.0F ? scale * original : original;
	}
}
