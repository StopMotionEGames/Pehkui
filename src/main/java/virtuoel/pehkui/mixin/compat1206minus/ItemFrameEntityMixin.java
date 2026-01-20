package virtuoel.pehkui.mixin.compat1206minus;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ItemFrame.class)
public abstract class ItemFrameEntityMixin
{
	@Dynamic
	@ModifyArg(method = MixinConstants.UPDATE_ATTACHMENT_POSITION, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/decoration/ItemFrame;setBoundingBox(Lnet/minecraft/world/phys/AABB;)V"))
	private AABB pehkui$updateAttachment$box(AABB box)
	{
		final HangingEntity entity = (HangingEntity) (Object) this;
		
		final Direction facing = entity.getDirection();
		
		final double xLength = box.getXsize() / -2.0D;
		final double yLength = box.getYsize() / -2.0D;
		final double zLength = box.getZsize() / -2.0D;
		
		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
		
		if (widthScale != 1.0F || heightScale != 1.0F)
		{
			final double dX = xLength * (1.0D - widthScale);
			final double dY = yLength * (1.0D - heightScale);
			final double dZ = zLength * (1.0D - widthScale);
			box = box.inflate(dX, dY, dZ);
			box = box.move(dX * facing.getStepX(), dY * facing.getStepY(), dZ * facing.getStepZ());
		}
		
		return box;
	}
}
