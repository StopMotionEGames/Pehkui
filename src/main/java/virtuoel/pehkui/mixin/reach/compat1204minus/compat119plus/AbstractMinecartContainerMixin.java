package virtuoel.pehkui.mixin.reach.compat1204minus.compat119plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecartContainer;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractMinecartContainer.class)
public abstract class AbstractMinecartContainerMixin
{
	@ModifyReturnValue(method = "canPlayerUse", at = @At("RETURN"))
	private boolean pehkui$canPlayerUse(boolean original, Player playerEntity)
	{
		if (!original)
		{
			final float scale = ScaleUtils.getEntityReachScale(playerEntity);
			
			final AbstractMinecartContainer self = (AbstractMinecartContainer) (Object) this;
			
			if (scale > 1.0F && !self.isRemoved() && self.position().closerThan(playerEntity.position(), 8.0 * scale))
			{
				return true;
			}
		}
		
		return original;
	}
}
