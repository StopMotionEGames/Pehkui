package virtuoel.pehkui.mixin.compat1212plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractBoat.class)
public abstract class AbstractBoatMixin
{
	@ModifyExpressionValue(method = "floatBoat", at = @At(value = "CONSTANT", args = "doubleValue=0.65D"))
	private double pehkui$updateVelocity$multiplier(double value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale != 1.0F ? value / scale : value;
	}
}
