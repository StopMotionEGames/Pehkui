package virtuoel.pehkui.mixin.compat1204minus;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

@Mixin(AbstractBoat.class)
public abstract class BoatMixin
{
	// todo: see if this is needed
//	@ModifyExpressionValue(method = "updateVelocity", at = @At(value = "CONSTANT", args = "doubleValue=0.06153846016296973D"))
//	private double pehkui$updateVelocity$multiplier(double value)
//	{
//		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
//
//		return scale != 1.0F ? scale * value : value;
//	}
}
