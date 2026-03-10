package virtuoel.pehkui.mixin.client.renderer.entity.player;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin {
	@ModifyReturnValue(method = "getRenderOffset(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)Lnet/minecraft/world/phys/Vec3;", at = @At("RETURN"))
	private Vec3 pehkui$getPositionOffset(Vec3 original, AvatarRenderState state)
	{
		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;
		if (original != Vec3.ZERO)
		{
			return original.scale(ext.pehkui$getModelHeighScale());
		}

		return original;
	}
}
