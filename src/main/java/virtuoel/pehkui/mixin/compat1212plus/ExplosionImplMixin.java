package virtuoel.pehkui.mixin.compat1212plus;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.ExplosionImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ExplosionImpl.class)
public abstract class ExplosionImplMixin
{
	@Shadow @Final @Mutable
	private float power;
	
	@Inject(at = @At("RETURN"), method = "<init>")
	private void pehkui$construct(ServerWorld world, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, Vec3d pos, float power, boolean createFire, Explosion.DestructionType destructionType, CallbackInfo ci)
	{
		if (entity != null)
		{
			final float scale = ScaleUtils.getExplosionScale(entity);
			
			if (scale != 1.0F)
			{
				this.power *= scale;
			}
		}
	}
}
