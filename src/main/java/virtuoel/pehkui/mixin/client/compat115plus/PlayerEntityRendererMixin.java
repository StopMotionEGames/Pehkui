package virtuoel.pehkui.mixin.client.compat115plus;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.render.entity.PlayerEntityRenderer;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
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
