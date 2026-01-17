package virtuoel.pehkui.mixin.compat116minus;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import virtuoel.pehkui.util.ReflectionUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Entity.class)
public abstract class EntityCalculateDimensionsMixin
{
	@Shadow
	private World world;
	@Shadow @Final @Mutable
	private EntityType<?> type;
	@Shadow
	public abstract void move(MovementType type, Vec3d movement);
	
	@Inject(method = "calculateDimensions", at = @At(value = "INVOKE", shift = Shift.AFTER, target = "Lnet/minecraft/entity/Entity;refreshPosition()V"))
	private void pehkui$calculateDimensions(CallbackInfo info, @Local(ordinal = 0) EntityDimensions previous, @Local(ordinal = 1) EntityDimensions current)
	{
		final float currentWidth = ReflectionUtils.getDimensionsWidth(current);
		final float previousWidth = ReflectionUtils.getDimensionsWidth(previous);
		if (this.world.isClient && type == EntityType.PLAYER && currentWidth > previousWidth)
		{
			final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
			final float dist = (previousWidth - currentWidth) / 2.0F;

			move(MovementType.SELF, new Vec3d(dist / scale, 0.0D, dist / scale));
		}
	}

	//todo: see if this is needed
//	@ModifyVariable(method = "recalculateDimensions", at = @At(value = "STORE"))
//	private float pehkui$calculateDimensions$vector(float value)
//	{
//		final float scale = ScaleUtils.getBoundingBoxWidthScale((Entity) (Object) this);
//
//		return (scale != 1.0F ? value / scale : value) / 2.0F;
//	}
}
