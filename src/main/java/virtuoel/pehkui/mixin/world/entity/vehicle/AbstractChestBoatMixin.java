package virtuoel.pehkui.mixin.world.entity.vehicle;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ChestBoat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractChestBoat;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractChestBoat.class)
public abstract class AbstractChestBoatMixin {
	@ModifyReturnValue(method = "getSinglePassengerXOffset", at = @At("RETURN"))
	private float pehkui$getPassengerHorizontalOffset(float original) {
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);

		return scale != 1.0F ? original * scale : original;
	}

	@ModifyReturnValue(method = "stillValid", at = @At("RETURN"))
	private boolean pehkui$canPlayerUse(boolean original, Player playerEntity)
	{
		if (!original)
		{
			final float scale = ScaleUtils.getEntityReachScale(playerEntity);

			final ChestBoat self = (ChestBoat) (Object) this;

			if (scale > 1.0F && !self.isRemoved() && self.position().closerThan(playerEntity.position(), 8.0 * scale))
			{
				return true;
			}
		}

		return original;
	}
}
