package virtuoel.pehkui.mixin.world.entity.projectile.throwableitemprojectile;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ThrowableItemProjectile.class)
public class ThrowableItemProjectileMixin {
	@ModifyArg(
		method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)V",
		index = 2,
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/throwableitemprojectile/ThrowableItemProjectile;<init>(Lnet/minecraft/world/entity/EntityType;DDDLnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)V"))
	private static double pehkui$construct$yOffset(double yOffset, @Local(argsOnly = true) LivingEntity owner) {
		final float scale = ScaleUtils.getEyeHeightScale(owner);

		return scale != 1.0F ? owner.getEyeY() - (0.1D * scale) : yOffset;
	}
}
