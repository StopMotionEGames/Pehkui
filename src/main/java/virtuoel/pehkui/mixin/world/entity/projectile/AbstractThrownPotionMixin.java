package virtuoel.pehkui.mixin.world.entity.projectile;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractThrownPotion.class)
public class AbstractThrownPotionMixin {
	@ModifyReturnValue(method = "getDefaultGravity", at = @At(value = "RETURN"))
	private double pehkui$getDefaultGravity(double gravity) {
		float scale = ScaleUtils.getMotionScale((Entity) (Object) this);

		return scale != 1.0F ? gravity * scale : gravity;
	}
}
