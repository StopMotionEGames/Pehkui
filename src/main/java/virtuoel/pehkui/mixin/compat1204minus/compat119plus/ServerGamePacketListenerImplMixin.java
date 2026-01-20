package virtuoel.pehkui.mixin.compat1204minus.compat119plus;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
	@Shadow ServerPlayer player;
	
	@ModifyArg(method = "handleUseItemOn", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;distanceToSqr(Lnet/minecraft/world/phys/Vec3;)D"))
	private Vec3 pehkui$onPlayerInteractBlock$center(Vec3 center)
	{
		final BlockPos pos = new BlockPos(Mth.floor(center.x), Mth.floor(center.y), Mth.floor(center.z));
		final Vec3 eye = player.getEyePosition();
		final BlockPos eyePos = new BlockPos(Mth.floor(eye.x), Mth.floor(eye.y), Mth.floor(eye.z));
		
		final double xOffset = eye.x() - center.x();
		final double yOffset = eye.y() - center.y();
		final double zOffset = eye.z() - center.z();
		
		center = center.add(
			eyePos.getX() == pos.getX() ? xOffset : xOffset < 0.0D ? -0.5D : 0.5D,
			eyePos.getY() == pos.getY() ? yOffset : yOffset < 0.0D ? -0.5D : 0.5D,
			eyePos.getZ() == pos.getZ() ? zOffset : zOffset < 0.0D ? -0.5D : 0.5D
		);
		
		return center;
	}
}
