package virtuoel.pehkui.mixin.compat1193minus.compat115plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Player.class)
public abstract class PlayerMixin
{
	@ModifyArg(method = "maybeBackOffFromEdge", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;move(DDD)Lnet/minecraft/world/phys/AABB;"))
	private double pehkui$adjustMovementForSneaking$stepHeight(double stepHeight)
	{
		final float scale = ScaleUtils.getStepHeightScale((Entity) (Object) this);
		
		return scale != 1.0F ? stepHeight * scale : stepHeight;
	}
}
