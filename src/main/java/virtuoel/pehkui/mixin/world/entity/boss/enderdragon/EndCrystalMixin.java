package virtuoel.pehkui.mixin.world.entity.boss.enderdragon;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin {
	@ModifyArg(method = "hurtServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Level$ExplosionInteraction;)V"))
	private float pehkui$damage$createExplosion(float power) {
		final float scale = ScaleUtils.getExplosionScale((Entity) (Object) this);

		return scale != 1.0F ? power * scale : power;
	}
}
