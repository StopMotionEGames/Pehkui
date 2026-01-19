package virtuoel.pehkui.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ProjectileEntity.class)
public abstract class ThrownEntityMixin
{
	@Inject(at = @At("RETURN"), method = "setOwner(Lnet/minecraft/entity/Entity;)V")
	private void pehkui$construct(Entity entity, CallbackInfo ci, @Local(argsOnly = true) Entity owner)
	{
		final float heightScale = ScaleUtils.getEyeHeightScale(owner);
		if (heightScale != 1.0F)
		{
			final Entity self = ((Entity) (Object) this);
			
			final Vec3d pos = self.getEntityPos();
			
			self.setPosition(pos.x, pos.y + ((1.0F - heightScale) * 0.1D), pos.z);
		}
		
		ScaleUtils.setScaleOfProjectile((Entity) (Object) this, owner);
	}
}
