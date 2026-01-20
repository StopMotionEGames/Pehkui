package virtuoel.pehkui.mixin.reach.compat115minus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Item.class)
public class ItemMixin
{
	@Dynamic
	@ModifyVariable(method = MixinConstants.ITEM_RAYCAST, ordinal = 1, at = @At(value = "STORE"))
	private static Vec3 pehkui$raycast$end(Vec3 value, Level world, Player player, ClipContext.Fluid fluidHandling)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		
		if (scale != 1.0F)
		{
			final Vec3 eyePos = ScaleUtils.getEyePos(player);
			final Vec3 distance = value.subtract(eyePos);
			
			return eyePos.add(distance.scale(scale));
		}
		
		return value;
	}
}
