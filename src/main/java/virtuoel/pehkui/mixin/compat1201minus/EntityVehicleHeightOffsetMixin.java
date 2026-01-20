package virtuoel.pehkui.mixin.compat1201minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ReflectionUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin({
	ArmorStand.class,
	AbstractSkeleton.class,
	Endermite.class,
	PatrollingMonster.class,
	Silverfish.class,
	Zombie.class,
	Animal.class,
	Player.class
})
public abstract class EntityVehicleHeightOffsetMixin
{
	@Dynamic
	@ModifyReturnValue(method = MixinConstants.GET_HEIGHT_OFFSET, at = @At("RETURN"))
	private double pehkui$getHeightOffset(double offset)
	{
		final Entity self = (Entity) (Object) this;
		final Entity vehicle = self.getVehicle();
		
		if (vehicle != null)
		{
			final float scale = ScaleUtils.getBoundingBoxHeightScale(self);
			final float vehicleScale = ScaleUtils.getBoundingBoxHeightScale(vehicle);
			
			if (scale != 1.0F || vehicleScale != 1.0F)
			{
				final double vehicleScaledHeight = vehicle.getBbHeight();
				final double vehicleHeight = vehicleScaledHeight / vehicleScale;
				final double scaledMountedOffset = ReflectionUtils.getMountedHeightOffset(vehicle);
				final double mountedOffset = scaledMountedOffset / vehicleScale;
				final double scaledOffset = offset * scale;
				
				final double bottom = vehicleHeight - mountedOffset - offset;
				final double down = vehicleScaledHeight - scaledMountedOffset - (bottom * scale);
				
				return (Math.max(down, scaledOffset));
			}
		}
		
		return offset;
	}
}
