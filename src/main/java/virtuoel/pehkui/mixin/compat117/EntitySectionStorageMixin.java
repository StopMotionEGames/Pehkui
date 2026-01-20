package virtuoel.pehkui.mixin.compat117;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.phys.AABB;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntitySectionStorage.class)
public class EntitySectionStorageMixin
{
	@Dynamic
	@WrapOperation(method = "method_31776", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/entity/EntityAccess;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
	private static AABB pehkui$intersecting$getBoundingBox(EntityAccess obj, Operation<AABB> original)
	{
		final AABB bounds = original.call(obj);
		
		if (obj instanceof Entity entity)
		{

			final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(entity);
			final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(entity);
			
			if (interactionWidth != 1.0F || interactionHeight != 1.0F)
			{
				final double scaledXLength = bounds.getXsize() * 0.5D * (interactionWidth - 1.0F);
				final double scaledYLength = bounds.getYsize() * 0.5D * (interactionHeight - 1.0F);
				final double scaledZLength = bounds.getZsize() * 0.5D * (interactionWidth - 1.0F);
				
				return bounds.inflate(scaledXLength, scaledYLength, scaledZLength);
			}
		}
		
		return bounds;
	}
}
