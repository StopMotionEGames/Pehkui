package virtuoel.pehkui.mixin.world.entity.ai.behavior;

import java.util.Optional;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(VillagerMakeLove.class)
public class VillagerMakeLoveMixin {
	@Inject(method = "breed", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"))
	private void pehkui$createChild(ServerLevel serverLevel, Villager villager, Villager villager2, CallbackInfoReturnable<Optional<Villager>> cir, @Local(ordinal = 2) Villager child) {
		ScaleUtils.loadAverageScales(child, villager, villager2);
	}
}
