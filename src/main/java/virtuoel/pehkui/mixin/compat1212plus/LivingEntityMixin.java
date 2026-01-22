package virtuoel.pehkui.mixin.compat1212plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@ModifyExpressionValue(method = "hurtServer", at = @At(value = "CONSTANT", args = "doubleValue=0.4000000059604645D"))
	private double pehkui$damage$knockback(double value, ServerLevel world, DamageSource source, float amount) {
		final float scale = ScaleUtils.getKnockbackScale(source.getEntity());

		return scale != 1.0F ? scale * value : value;
	}

	@ModifyExpressionValue(method = "blockedByShield(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At(value = "CONSTANT", args = "doubleValue=0.5D"))
	private double pehkui$knockback$knockback(double value, LivingEntity target) {
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);

		return scale != 1.0F ? scale * value : value;
	}
}
