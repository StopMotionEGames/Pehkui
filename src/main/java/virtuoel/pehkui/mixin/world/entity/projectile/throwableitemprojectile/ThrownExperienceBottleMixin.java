package virtuoel.pehkui.mixin.world.entity.projectile.throwableitemprojectile;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownExperienceBottle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ThrownExperienceBottle.class)
public class ThrownExperienceBottleMixin {
	@ModifyReturnValue(method = "getDefaultGravity", at = @At(value = "RETURN"))
	private double pehkui$getDefaultGravity(double gravity) {
		float scale = ScaleUtils.getMotionScale((Entity) (Object) this);

		return scale != 1.0F ? gravity * scale : gravity;
	}

}
