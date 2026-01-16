package virtuoel.pehkui.mixin.compat116plus;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.passive.PigEntity;

@Mixin(PigEntity.class)
public class PigEntityMixin
{
	// todo see!
//	@Inject(method = "onStruckByLightning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/LightningEntity;)V", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/entity/mob/ZombifiedPiglinEntity;refreshPositionAndAngles(DDDFF)V"))
//	private void pehkui$onStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo info, @Local ZombifiedPiglinEntity zombifiedPiglinEntity)
//	{
//		ScaleUtils.loadScale(zombifiedPiglinEntity, (Entity) (Object) this);
//	}
}
