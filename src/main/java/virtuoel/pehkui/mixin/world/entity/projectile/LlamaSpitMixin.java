package virtuoel.pehkui.mixin.world.entity.projectile;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LlamaSpit.class)
public class LlamaSpitMixin {
	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/animal/equine/Llama;)V")
	private void pehkui$construct(Level level, Llama owner, CallbackInfo ci) {
		ScaleUtils.setScaleOfProjectile((Entity) (Object) this, owner);
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/animal/equine/Llama;)V", at = @At(value = "CONSTANT", args = "doubleValue=0.10000000149011612D"))
	private double pehkui$construct$eyeOffset(double value, Level world, Llama owner) {
		final float scale = ScaleUtils.getEyeHeightScale(owner);

		return scale != 1.0F ? value * scale : value;
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/animal/equine/Llama;)V", at = @At(value = "CONSTANT", args = "floatValue=1.0F"))
	private float pehkui$construct$widthOffset(float value, Level world, Llama owner) {
		final float scale = ScaleUtils.getBoundingBoxWidthScale(owner);

		return scale != 1.0F ? value * scale : value;
	}
}
