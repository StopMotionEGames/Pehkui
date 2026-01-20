package virtuoel.pehkui.mixin.compat115minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin
{
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.TRAVEL, at = @At(value = "CONSTANT", args = "floatValue=4.0F"))
	private float pehkui$travel$limbDistance(float value)
	{
		return ScaleUtils.modifyLimbDistance(value, (Entity) (Object) this);
	}
	
	@Unique Vec3 pehkui$initialClimbingPos = null;
	
	@ModifyReturnValue(method = "onClimbable()Z", at = @At("RETURN"))
	private boolean pehkui$isClimbing(boolean original)
	{
		final LivingEntity self = (LivingEntity) (Object) this;
		
		if (pehkui$initialClimbingPos != null || original || self.isSpectator())
		{
			return original;
		}
		
		final float width = ScaleUtils.getBoundingBoxWidthScale(self);
		
		if (width > 1.0F)
		{
			final AABB bounds = self.getBoundingBox();
			
			final double halfUnscaledXLength = (bounds.getXsize() / width) / 2.0D;
			final int minX = Mth.floor(bounds.minX + halfUnscaledXLength);
			final int maxX = Mth.floor(bounds.maxX - halfUnscaledXLength);
			
			final int minY = Mth.floor(bounds.minY);
			
			final double halfUnscaledZLength = (bounds.getZsize() / width) / 2.0D;
			final int minZ = Mth.floor(bounds.minZ + halfUnscaledZLength);
			final int maxZ = Mth.floor(bounds.maxZ - halfUnscaledZLength);
			
			pehkui$initialClimbingPos = method_5812();
			
			for (final BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, minY, maxZ))
			{
				setPosDirectly(pos.getX(), pos.getY(), pos.getZ());
				if (self.onClimbable())
				{
					return true;
				}
			}
			
			setPosDirectly(pehkui$initialClimbingPos.x(), pehkui$initialClimbingPos.y(), pehkui$initialClimbingPos.z());
			pehkui$initialClimbingPos = null;
		}
		
		return original;
	}
}
