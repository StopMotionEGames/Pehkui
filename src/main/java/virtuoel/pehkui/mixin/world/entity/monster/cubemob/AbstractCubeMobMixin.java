package virtuoel.pehkui.mixin.world.entity.monster.cubemob;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(net.minecraft.world.entity.monster.cubemob.AbstractCubeMob.class)
public class AbstractCubeMobMixin
{
	@ModifyExpressionValue(method = "setUpSplitCube", at = @At(value = "CONSTANT", args = "doubleValue=0.5D"))
	private double pehkui$remove$verticalOffset(double value)
	{
		final double scale = ScaleUtils.getBoundingBoxHeightScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			return value * scale;
		}
		
		return value;
	}
}
