package virtuoel.pehkui.mixin.compat117minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Boat.class)
public abstract class BoatMixin
{
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.UPDATE_PASSENGER_POSITION, at = @At(value = "CONSTANT", args = "doubleValue=0.2D"))
	private double pehkui$updatePassengerPosition$animalOffset(double value, Entity passenger)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale(passenger);
		
		return scale != 1.0F ? scale * value : value;
	}
}
