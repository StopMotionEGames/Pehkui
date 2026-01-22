package virtuoel.pehkui.mixin.compat1194plus;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
	@ModifyArg(method = "calculateEntityAnimation(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;updateWalkAnimation(F)V"))
	private float pehkui$updateLimbs(float value)
	{
		return ScaleUtils.modifyLimbDistance(value, (LivingEntity) (Object) this);
	}
}
