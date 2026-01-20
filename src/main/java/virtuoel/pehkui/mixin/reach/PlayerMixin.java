package virtuoel.pehkui.mixin.reach;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public abstract class PlayerMixin
{
	//todo: see if entity reach doesn't break
//	@ModifyExpressionValue(method = "attack", at = @At(value = "CONSTANT", args = "doubleValue=9.0F"))
//	private double pehkui$attack$distance(double value)
//	{
//		final float scale = ScaleUtils.getEntityReachScale((Entity) (Object) this);
//
//		return scale > 1.0F ? scale * scale * value : value;
//	}
}
