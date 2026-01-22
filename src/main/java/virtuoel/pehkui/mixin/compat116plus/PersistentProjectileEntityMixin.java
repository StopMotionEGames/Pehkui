package virtuoel.pehkui.mixin.compat116plus;

import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractArrow.class)
public abstract class PersistentProjectileEntityMixin
{
	@ModifyArg(method = "findHitEntity", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getEntityHitResult(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Lnet/minecraft/world/phys/EntityHitResult;"))
	private AABB pehkui$getEntityCollision$expand(Level world, Entity entity, Vec3 vec3d, Vec3 vec3d2, AABB box, Predicate<Entity> predicate)
	{
		final float width = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float height = ScaleUtils.getBoundingBoxHeightScale(entity);
		
		if (width != 1.0F || height != 1.0F)
		{
			return box.inflate(width - 1.0D, height - 1.0D, width - 1.0D);
		}
		
		return box;
	}
	
	@ModifyVariable(method = "onHitEntity", at = @At(value = "STORE"))
	private float pehkui$onEntityHit(float value)
	{
		final float scale = ScaleUtils.getMotionScale((Entity) (Object) this);
		
		return scale != 1.0F ? ScaleUtils.divideClamped(value, scale) : value;
	}
}
