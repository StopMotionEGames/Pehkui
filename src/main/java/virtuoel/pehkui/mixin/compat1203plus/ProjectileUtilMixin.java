package virtuoel.pehkui.mixin.compat1203plus;

import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin
{
	@ModifyArg(method = "getHitResult(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/Entity;Ljava/util/function/Predicate;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/Level;FLnet/minecraft/world/level/ClipContext$Block;)Lnet/minecraft/world/phys/HitResult;", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/ProjectileUtil;getEntityHitResult(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;F)Lnet/minecraft/world/phys/EntityHitResult;"))
	private static AABB pehkui$getCollision$expand(Level world, Entity entity, Vec3 min, Vec3 max, AABB box, Predicate<Entity> predicate, float margin)
	{
		final float width = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float height = ScaleUtils.getBoundingBoxHeightScale(entity);
		
		final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(entity);
		final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(entity);
		
		if (width != 1.0F || height != 1.0F || interactionWidth != 1.0F || interactionHeight != 1.0F)
		{
			return box.inflate((width * interactionWidth) - 1.0D, (height * interactionHeight) - 1.0D, (width * interactionWidth) - 1.0D);
		}
		
		return box;
	}
}
