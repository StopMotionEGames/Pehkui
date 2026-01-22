package virtuoel.pehkui.mixin.world.entity.projectile;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Projectile.class)
public abstract class ProjectileMixin
{
	@Inject(at = @At("RETURN"), method = "setOwner(Lnet/minecraft/world/entity/Entity;)V")
	private void pehkui$construct(Entity entity, CallbackInfo ci, @Local(argsOnly = true) Entity owner)
	{
		final float heightScale = ScaleUtils.getEyeHeightScale(owner);
		if (heightScale != 1.0F)
		{
			final Entity self = ((Entity) (Object) this);
			
			final Vec3 pos = self.position();
			
			self.setPos(pos.x, pos.y + ((1.0F - heightScale) * 0.1D), pos.z);
		}
		
		ScaleUtils.setScaleOfProjectile((Entity) (Object) this, owner);
	}
}
