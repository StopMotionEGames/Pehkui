package virtuoel.pehkui.mixin.world.entity.monster;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(targets = "net.minecraft.world.entity.monster.Evoker$EvokerSummonSpellGoal")
public class EvokerSummonSpellGoal {
	@ModifyArg(method = "performSpellCasting()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntityWithPassengers(Lnet/minecraft/world/entity/Entity;)V"))
	private Entity pehkui$castSpell$spawnEntityAndPassengers(Entity entity) {
		if (entity instanceof Vex) {
			ScaleUtils.loadScale(entity, ((Vex) entity).getOwner());
		}

		return entity;
	}
}
