package virtuoel.pehkui.mixin.compat115minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.fox.Fox;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Pseudo
@Mixin(targets = MixinConstants.FOX_ENTITY_MATE_GOAL)
public abstract class FoxEntityMateGoalMixin extends BreedGoal
{
	private FoxEntityMateGoalMixin()
	{
		super(null, 0);
	}
	
	@Dynamic
	@Inject(method = MixinConstants.BREED, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.SPAWN_ENTITY))
	private void pehkui$breed(CallbackInfo info, @Local Fox foxEntity)
	{
		ScaleUtils.loadAverageScales(foxEntity, this.animal, this.partner);
	}
}
