package virtuoel.pehkui.mixin.compat1216plus;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin
{
	@ModifyArg(method = "getEntityCollision", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;getEntityCollision(Lnet/minecraft/world/World;Lnet/minecraft/entity/projectile/ProjectileEntity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Lnet/minecraft/util/hit/EntityHitResult;"))
	private Box pehkui$getEntityCollision$expand(Box box)
	{
		ProjectileEntity projectile = (ProjectileEntity) (Object) this;
		final float width = ScaleUtils.getBoundingBoxWidthScale(projectile);
		final float height = ScaleUtils.getBoundingBoxHeightScale(projectile);
		
		if (width != 1.0F || height != 1.0F)
		{
			return box.expand(width - 1.0D, height - 1.0D, width - 1.0D);
		}
		
		return box;
	}
	
	@ModifyVariable(method = "onEntityHit", at = @At(value = "STORE"))
	private float pehkui$onEntityHit(float value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale != 1.0F ? ScaleUtils.divideClamped(value, scale) : value;
	}
}
