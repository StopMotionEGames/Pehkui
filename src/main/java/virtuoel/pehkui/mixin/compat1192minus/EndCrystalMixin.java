package virtuoel.pehkui.mixin.compat1192minus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin
{
	@Dynamic
	@ModifyArg(method = "hurtServer", at = @At(value = "INVOKE", target = MixinConstants.CREATE_EXPLOSION))
	private float pehkui$damage$createExplosion(float power)
	{
		final float scale = ScaleUtils.getExplosionScale((Entity) (Object) this);
		
		return scale != 1.0F ? power * scale : power;
	}
}
