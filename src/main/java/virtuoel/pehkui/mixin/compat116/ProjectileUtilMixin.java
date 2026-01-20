package virtuoel.pehkui.mixin.compat116;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin
{
	@WrapOperation(method = "getEntityHitResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
	private static AABB pehkui$getEntityCollision$getBoundingBox(Entity obj, Operation<AABB> original)
	{
		final AABB bounds = original.call(obj);
		
		final float width = ScaleUtils.getBoundingBoxWidthScale(obj);
		final float height = ScaleUtils.getBoundingBoxHeightScale(obj);
		
		final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(obj);
		final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(obj);
		
		if (width != 1.0F || height != 1.0F || interactionWidth != 1.0F || interactionHeight != 1.0F)
		{
			final double scaledXLength = 0.3D * ((width * interactionWidth) - 1.0F);
			final double scaledYLength = 0.3D * ((height * interactionHeight) - 1.0F);
			final double scaledZLength = 0.3D * ((width * interactionWidth) - 1.0F);
			
			return bounds.inflate(scaledXLength, scaledYLength, scaledZLength);
		}
		
		return bounds;
	}
}
