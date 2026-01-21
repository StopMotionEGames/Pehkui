package virtuoel.pehkui.mixin.client.renderer.entity.player;

import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin
{
	// todo: see this!
//	@ModifyReturnValue(method = "getPositionOffset(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)Lnet/minecraft/util/math/Vec3d;", at = @At("RETURN"))
//	private Vec3d pehkui$getPositionOffset(Vec3d original)
//	{
//		if (original != Vec3d.ZERO)
//		{
//			return original.multiply(ScaleUtils.getModelHeightScale(entity, tickProgress));
//		}
//
//		return original;
//	}
}
