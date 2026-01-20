package virtuoel.pehkui.mixin.compat1204minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.Shulker;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Shulker.class)
public class ShulkerMixin
{
	@Dynamic
	@ModifyReturnValue(method = MixinConstants.GET_ACTIVE_EYE_HEIGHT, at = @At("RETURN"))
	private float pehkui$getActiveEyeHeight(float original, Pose pose, EntityDimensions dimensions)
	{
		final Shulker entity = (Shulker) (Object) this;
		
		final Direction face = entity.getAttachFace();
		if (face != Direction.DOWN)
		{
			final float scale = ScaleUtils.getEyeHeightScale(entity);
			if (scale != 1.0F)
			{
				if (face == Direction.UP)
				{
					return ScaleUtils.divideClamped(1.0F, scale) - original;
				}
				else
				{
					return ScaleUtils.divideClamped(1.0F - original, scale);
				}
			}
		}
		
		return original;
	}
}
