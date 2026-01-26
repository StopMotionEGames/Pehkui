package virtuoel.pehkui.mixin.world.entity.projectile.throwableitemprojectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownLingeringPotion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ThrownLingeringPotion.class)
public class ThrownLingeringPotionMixin {
	@ModifyArg(method = "onHitAsPotion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/AreaEffectCloud;setRadius(F)V"))
	private float pehkui$onHitAsPotion$setRadius(float value) {
		return value * ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
	}

	@ModifyArg(method = "onHitAsPotion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/AreaEffectCloud;setRadiusOnUse(F)V"))
	private float pehkui$onHitAsPotion$setRadiusOnUse(float value) {
		return value * ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
	}

	@ModifyArg(method = "onHitAsPotion", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
	private Entity pehkui$onHitAsPotion$entity(Entity entity) {
		ScaleUtils.loadScale(entity, (Entity) (Object) this);

		return entity;
	}
}
