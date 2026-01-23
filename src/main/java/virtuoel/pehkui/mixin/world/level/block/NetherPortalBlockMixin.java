package virtuoel.pehkui.mixin.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.PehkuiConfig;
import virtuoel.pehkui.util.PehkuiBlockStateExtensions;

@Mixin(NetherPortalBlock.class)
public abstract class NetherPortalBlockMixin {
	@Inject(at = @At("HEAD"), method = "entityInside", cancellable = true)
	private void pehkui$onEntityCollision(BlockState blockState, Level level, BlockPos blockPos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, boolean bl, CallbackInfo ci) {
		if (PehkuiConfig.COMMON.accurateNetherPortals.get()) {
			if (!entity.getBoundingBox().intersects(((PehkuiBlockStateExtensions) blockState).pehkui_getOutlineShape(level, blockPos).bounds().move(blockPos))) {
				ci.cancel();
			}
		}
	}
}
