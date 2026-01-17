package virtuoel.pehkui.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin
{
	@Inject(at = @At("HEAD"), method = "copyFrom")
	private void pehkui$copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info)
	{
		ScaleUtils.loadScaleOnRespawn((ServerPlayerEntity) (Object) this, oldPlayer, alive);
	}
	@Inject(at = @At("RETURN"), method = "dropItem")
	private void pehkui$dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir)
	{
		final ItemEntity entity = cir.getReturnValue();

		if (entity != null)
		{
			Entity self = (Entity) (Object) this;
			ScaleUtils.setScaleOfDrop(entity, self);

			final float scale = ScaleUtils.getEyeHeightScale(self);

			if (scale != 1.0F)
			{
				final Vec3d pos = entity.getPos();

				entity.setPosition(pos.x, pos.y + ((1.0F - scale) * 0.3D), pos.z);
			}
		}
	}
}
