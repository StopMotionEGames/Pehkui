package virtuoel.pehkui.mixin.compat1193minus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ReflectionUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerMixin
{
	@Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", ordinal = 0, shift = Shift.BEFORE, target = "Lnet/minecraft/world/entity/LivingEntity;travel(Lnet/minecraft/world/phys/Vec3;)V"))
	private void pehkui$travel$flightSpeed(Vec3 movementInput, CallbackInfo info)
	{
		final Player self = (Player) (Object) this;
		final float scale = ScaleUtils.getFlightScale(self);
		
		if (scale != 1.0F)
		{
			ReflectionUtils.setFlyingSpeed(self, ReflectionUtils.getFlyingSpeed(self) * scale);
		}
	}
}
