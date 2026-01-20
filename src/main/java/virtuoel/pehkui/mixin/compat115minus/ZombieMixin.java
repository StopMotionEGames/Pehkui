package virtuoel.pehkui.mixin.compat115minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Zombie.class)
public class ZombieMixin
{
	@Dynamic
	@Inject(method = MixinConstants.CONVERT_TO, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.ZOMBIE_COPY_POS_AND_ROT))
	private void pehkui$convertTo(EntityType<? extends Zombie> entityType, CallbackInfo info, @Local Zombie zombieEntity)
	{
		ScaleUtils.loadScale(zombieEntity, (Entity) (Object) this);
	}
	
	@Dynamic
	@Inject(method = MixinConstants.ON_KILLED_OTHER, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.ZOMBIE_VILLAGER_COPY_POS_AND_ROT))
	private void pehkui$onKilledOther(LivingEntity other, CallbackInfo info, @Local Villager villagerEntity, @Local ZombieVillager zombieVillagerEntity)
	{
		ScaleUtils.loadScale(zombieVillagerEntity, villagerEntity);
	}
}
