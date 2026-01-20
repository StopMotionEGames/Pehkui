package virtuoel.pehkui.mixin.compat1204minus.compat117plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Slime.class)
public class SlimeMixin
{
	@ModifyExpressionValue(method = "remove(Lnet/minecraft/world/entity/Entity$RemovalReason;)V", at = @At(value = "CONSTANT", args = "floatValue=4.0F"))
	private float pehkui$remove$horizontalOffset(float value)
	{
		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			return value / scale;
		}
		
		return value;
	}
}
