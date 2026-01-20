package virtuoel.pehkui.mixin.reach.compat1204minus.compat1194plus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(value = Container.class, priority = 990)
public interface ContainerMixin
{
	/**
	 * Computes reach distance from eye position to the edge of the block
	 * instead of to the center of the block
	 */
	@Dynamic
	@Overwrite // UNMAPPED_METHOD
	static boolean method_49106(BlockEntity blockEntity, Player player, int range)
	{
		Level world = blockEntity.getLevel();
		BlockPos pos = blockEntity.getBlockPos();
		
		if (world == null)
		{
			return false;
		}
		else if (world.getBlockEntity(pos) != blockEntity)
		{
			return false;
		}
		else
		{
			double x = ((double) pos.getX()) + 0.5D;
			double y = ((double) pos.getY()) + 0.5D;
			double z = ((double) pos.getZ()) + 0.5D;
			final Vec3 eyePos = player.getEyePosition();
			x = (x - 0.5D) + ScaleUtils.getBlockXOffset(pos, player) - (eyePos.x() - player.getX());
			y = (y - 0.5D) + ScaleUtils.getBlockYOffset(pos, player) - (eyePos.y() - player.getY());
			z = (z - 0.5D) + ScaleUtils.getBlockZOffset(pos, player) - (eyePos.z() - player.getZ());
			final double reach = ScaleUtils.getBlockReachScale(player) * (double) range;
			return player.distanceToSqr(x, y, z) <= reach * reach;
		}
	}
}
