package virtuoel.pehkui.mixin.compat1212plus;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerExplosion.class)
public abstract class ServerExplosionMixin
{
	@Shadow @Final @Mutable
	private float radius;
	
	@Inject(at = @At("RETURN"), method = "<init>")
	private void pehkui$construct(ServerLevel world, Entity entity, DamageSource damageSource, ExplosionDamageCalculator behavior, Vec3 pos, float power, boolean createFire, Explosion.BlockInteraction destructionType, CallbackInfo ci)
	{
		if (entity != null)
		{
			final float scale = ScaleUtils.getExplosionScale(entity);
			
			if (scale != 1.0F)
			{
				this.radius *= scale;
			}
		}
	}
}
