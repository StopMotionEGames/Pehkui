package virtuoel.pehkui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EnderDragon.class)
public class EnderDragonMixin
{
	@ModifyArg(method = "onCrystalDestroyed", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;hurt(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/boss/enderdragon/EnderDragonPart;Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
	private float pehkui$crystalDestroyed$damagePart(float amount, @Local(argsOnly = true) DamageSource damageSource)
	{
		Entity attacker = damageSource.getEntity();
		if (attacker != null)
		{
			final float scale = ScaleUtils.getAttackScale(attacker);
			
			if (scale != 1.0F)
			{
				return amount / scale;
			}
		}
		
		return amount;
	}
}
