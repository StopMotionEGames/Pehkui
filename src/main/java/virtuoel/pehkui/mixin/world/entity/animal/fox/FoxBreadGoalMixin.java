package virtuoel.pehkui.mixin.world.entity.animal.fox;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.fox.Fox;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(targets = "net.minecraft.world.entity.animal.fox.Fox$FoxBreedGoal")
public abstract class FoxBreadGoalMixin extends BreedGoal
{
	private FoxBreadGoalMixin()
	{
		super(null, 0);
	}
	
	@Inject(method = "breed()V", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"))
	private void pehkui$breed(CallbackInfo info, @Local Fox foxEntity)
	{
		ScaleUtils.loadAverageScales(foxEntity, this.animal, this.partner);
	}
}
