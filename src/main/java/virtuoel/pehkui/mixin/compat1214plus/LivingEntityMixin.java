package virtuoel.pehkui.mixin.compat1214plus;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
	@ModifyArg(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"),index = 0)
	private double pehkui$damage$knockback(double strength, @Local(argsOnly = true) DamageSource source)
	{
		final float scale = ScaleUtils.getKnockbackScale(source.getAttacker());
		
		return scale != 1.0F ? scale * strength : strength;
	}
	
	@ModifyArg(method = "knockback(Lnet/minecraft/entity/LivingEntity;)V", at = @At(value = "INVOKE", args = "floatValue=0.5F", target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"), index = 0)
	private double pehkui$knockback$knockback(double value, @Local(argsOnly = true) LivingEntity target)
	{
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
