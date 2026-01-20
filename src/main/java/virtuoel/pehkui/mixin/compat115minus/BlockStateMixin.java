package virtuoel.pehkui.mixin.compat115minus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import virtuoel.pehkui.util.PehkuiBlockStateExtensions;

@Mixin(BlockState.class)
public abstract class BlockStateMixin implements PehkuiBlockStateExtensions
{
	@Dynamic @Shadow
	abstract VoxelShape method_17770(BlockGetter world, BlockPos pos); // UNMAPPED_METHOD
	
	@Override
	public VoxelShape pehkui_getOutlineShape(BlockGetter world, BlockPos pos)
	{
		return method_17770(world, pos);
	}
	
	@Dynamic @Shadow
	abstract Block method_11614();
	
	@Override
	public Block pehkui_getBlock()
	{
		return method_11614();
	}
}
