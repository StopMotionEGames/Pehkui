package virtuoel.pehkui.mixin.compat1206minus;

import net.minecraft.world.entity.decoration.HangingEntity;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.MixinConstants;

@Mixin(HangingEntity.class)
public abstract class AbstractDecorationEntityMixin
{
	@Shadow
	protected abstract void recalculateBoundingBox();
	
	@Dynamic
	@Inject(at = @At("RETURN"), method = MixinConstants.CALCULATE_DIMENSIONS)
	private void pehkui$calculateDimensions(CallbackInfo info)
	{
		recalculateBoundingBox();
	}
}
