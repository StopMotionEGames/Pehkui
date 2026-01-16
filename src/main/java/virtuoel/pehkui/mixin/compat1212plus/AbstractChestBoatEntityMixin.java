package virtuoel.pehkui.mixin.compat1212plus;

import net.minecraft.entity.vehicle.AbstractChestBoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.Entity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractChestBoatEntity.class)
public abstract class AbstractChestBoatEntityMixin
{
	@ModifyReturnValue(method = "getPassengerHorizontalOffset", at = @At("RETURN"))
	private float pehkui$getPassengerHorizontalOffset(float original)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		
		return scale != 1.0F ? original * scale : original;
	}
}
