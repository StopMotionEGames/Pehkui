package virtuoel.pehkui.mixin.compat115minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ZombieVillager.class)
public class ZombieVillagerMixin
{
	@Dynamic
	@Inject(method = MixinConstants.FINISH_CONVERSION, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.VILLAGER_COPY_POS_AND_ROT))
	private void pehkui$finishConversion(ServerLevel world, CallbackInfo info, @Local Villager villagerEntity)
	{
		ScaleUtils.loadScale(villagerEntity, (Entity) (Object) this);
	}
}
