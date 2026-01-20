package virtuoel.pehkui.mixin.compat1204minus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(WitherBoss.class)
public class WitherBossMixin
{
	@ModifyExpressionValue(method = "getHeadY", at = { @At(value = "CONSTANT", args = "doubleValue=3.0D"), @At(value = "CONSTANT", args = "doubleValue=2.2D") })
	private double pehkui$getHeadY$offset(double value)
	{
		final float scale = ScaleUtils.getBoundingBoxHeightScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * value : value;
	}
}
