package virtuoel.pehkui.mixin.compat115minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AgeableMob.class)
public class AgeableMobMixin
{
	@Dynamic
	@Inject(method = MixinConstants.INTERACT_MOB, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.PASSIVE_ENTITY_REFRESH_POS_AND_ANGLES))
	private void pehkui$interactMob(Player player, InteractionHand hand, CallbackInfoReturnable<Boolean> info, @Local AgeableMob passiveEntity)
	{
		ScaleUtils.loadScale(passiveEntity, (Entity) (Object) this);
	}
}
