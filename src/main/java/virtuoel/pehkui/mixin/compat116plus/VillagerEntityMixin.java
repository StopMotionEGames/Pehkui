package virtuoel.pehkui.mixin.compat116plus;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.passive.VillagerEntity;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin
{
//	@Inject(method = "onStruckByLightning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LightningEntity;)V", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/entity/mob/WitchEntity;refreshPositionAndAngles(DDDFF)V"))
//	private void pehkui$onStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo info, @Local WitchEntity witchEntity)
//	{
//		ScaleUtils.loadScale(witchEntity, (Entity) (Object) this);
//	}
}
