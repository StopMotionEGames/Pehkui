package virtuoel.pehkui.mixin.compat1204minus.compat1193plus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.camel.Camel;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Camel.class)
public class CamelMixin
{
	@Dynamic
	@ModifyReturnValue(method = MixinConstants.GET_DIMENSIONS, at = @At("RETURN"))
	private EntityDimensions pehkui$getDimensions(EntityDimensions original, Pose pose)
	{
		if (pose == Pose.SITTING)
		{
			original = original.scale(ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this), ScaleUtils.getBoundingBoxHeightScale((Entity) (Object) this));
		}
		
		return original;
	}
}
