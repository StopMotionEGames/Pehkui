package virtuoel.pehkui.mixin.compat119minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.Llama;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Llama.class)
public abstract class LlamaMixin
{
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.UPDATE_PASSENGER_POSITION, at = @At(value = "CONSTANT", args = "floatValue=0.3F"))
	private float pehkui$updatePassengerPosition$offset(float value)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
