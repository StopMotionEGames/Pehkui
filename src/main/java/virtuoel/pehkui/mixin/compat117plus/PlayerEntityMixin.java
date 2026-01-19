package virtuoel.pehkui.mixin.compat117plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
	// see if still works, and than copy to 12111plus, and put this in 12110minus
	@ModifyArg(method = "attack(Lnet/minecraft/entity/Entity;)V", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;knockbackTarget(Lnet/minecraft/entity/Entity;FLnet/minecraft/util/math/Vec3d;)V"))
	private float pehkui$attack$knockback(float strength)
	{
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * strength : strength;
	}
}
