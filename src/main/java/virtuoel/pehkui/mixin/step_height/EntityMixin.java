package virtuoel.pehkui.mixin.step_height;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Entity.class)
public abstract class EntityMixin
{
	@ModifyReturnValue(method = "maxUpStep()F", at = @At("RETURN"))
	private float pehkui$getStepHeight(float original)
	{
		final float scale = ScaleUtils.getStepHeightScale((Entity) (Object) this);
		
		return scale != 1.0F ? original * scale : original;
	}
}
