package virtuoel.pehkui.mixin.compat1204minus.compat117plus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ContainerOpenersCounter.class)
public class ContainerOpenersCounterMixin
{
	@Shadow
	int openCount;
	
	@Unique
	float viewerSearchRange = 5.0F;
	
	@Inject(at = @At("HEAD"), method = "openContainer(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V")
	private void pehkui$openContainer(Player player, Level world, BlockPos pos, BlockState state, CallbackInfo info)
	{
		if (openCount < 0)
		{
			openCount = 0;
			
			viewerSearchRange = 5.0F;
		}
		
		final float scale = ScaleUtils.getBlockReachScale(player);
		
		if (scale != 1.0F)
		{
			final float nextRange = 5.0F * scale;
			
			if (nextRange > viewerSearchRange)
			{
				viewerSearchRange = nextRange;
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "closeContainer(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V")
	private void pehkui$closeContainer(Player player, Level world, BlockPos pos, BlockState state, CallbackInfo info)
	{
		if (openCount <= 1)
		{
			openCount = 1;
			
			viewerSearchRange = 5.0F;
		}
	}
	
	@Dynamic
	@ModifyExpressionValue(method = MixinConstants.GET_IN_RANGE_VIEWER_COUNT, at = @At(value = "CONSTANT", args = "floatValue=5.0F"))
	private float pehkui$getInRangeViewerCount$range(float value)
	{
		return viewerSearchRange;
	}
}
