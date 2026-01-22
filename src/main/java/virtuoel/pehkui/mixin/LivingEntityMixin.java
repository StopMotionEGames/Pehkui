package virtuoel.pehkui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import virtuoel.pehkui.util.MulticonnectCompatibility;
import virtuoel.pehkui.util.PehkuiBlockStateExtensions;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
	@Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V", shift = Shift.AFTER))
	private void pehkui$tickMovement$minVelocity(CallbackInfo info, @Local Vec3 velocity)
	{
		final LivingEntity self = (LivingEntity) (Object) this;
		
		final float scale = ScaleUtils.getMotionScale(self);
		
		if (scale < 1.0F)
		{
			final double min = scale * MulticonnectCompatibility.INSTANCE.getProtocolDependentValue(ver -> ver <= 47, 0.005D, 0.003D);
			
			double vX = velocity.x;
			double vY = velocity.y;
			double vZ = velocity.z;
			
			if (Math.abs(vX) < min)
			{
				vX = 0.0D;
			}
			
			if (Math.abs(vY) < min)
			{
				vY = 0.0D;
			}
			
			if (Math.abs(vZ) < min)
			{
				vZ = 0.0D;
			}
			
			self.setDeltaMovement(vX, vY, vZ);
		}
	}
	
	@ModifyVariable(method = "getDamageAfterArmorAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F", at = @At("HEAD"), argsOnly = true)
	private float pehkui$applyArmorToDamage(float value, DamageSource source, float amount)
	{
		final Entity attacker = source.getEntity();
		final float attackScale = attacker == null ? 1.0F : ScaleUtils.getAttackScale(attacker);
		final float defenseScale = ScaleUtils.getDefenseScale((Entity) (Object) this);
		
		if (attackScale != 1.0F || defenseScale != 1.0F)
		{
			value = attackScale * value / defenseScale;
		}
		
		return value;
	}
	
	@ModifyReturnValue(method = "getMaxHealth", at = @At("RETURN"))
	private float pehkui$getMaxHealth(float original)
	{
		final float scale = ScaleUtils.getHealthScale((Entity) (Object) this);
		
		return scale != 1.0F ? original * scale : original;
	}
	
	@ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
	private double pehkui$getAttackDistanceScalingFactor(double original)
	{
		final float scale = ScaleUtils.getVisibilityScale((Entity) (Object) this);
		
		return scale != 1.0F ? original * scale : original;
	}
	
	@ModifyReturnValue(method = "handleOnClimbable(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", at = @At("RETURN"))
	private Vec3 pehkui$applyClimbingSpeed(Vec3 original)
	{
		final LivingEntity self = (LivingEntity) (Object) this;
		
		if (!self.onClimbable())
		{
			return original;
		}
		
		final float width = ScaleUtils.getBoundingBoxWidthScale(self);
		
		if (width > 1.0F)
		{
			final AABB bounds = self.getBoundingBox();
			
			final double halfUnscaledXLength = (bounds.getXsize() / width) / 2.0D;
			final int minX = Mth.floor(bounds.minX + halfUnscaledXLength);
			final int maxX = Mth.floor(bounds.maxX - halfUnscaledXLength);
			
			final int minY = Mth.floor(bounds.minY);
			
			final double halfUnscaledZLength = (bounds.getZsize() / width) / 2.0D;
			final int minZ = Mth.floor(bounds.minZ + halfUnscaledZLength);
			final int maxZ = Mth.floor(bounds.maxZ - halfUnscaledZLength);
			
			final Level world = self.getCommandSenderWorld();
			
			for (final BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, minY, maxZ))
			{
				if (((PehkuiBlockStateExtensions) world.getBlockState(pos)).pehkui_getBlock() instanceof ScaffoldingBlock)
				{
					return new Vec3(original.x, Math.max(self.getDeltaMovement().y, -0.15D), original.z);
				}
			}
		}
		
		return original;
	}
	
	@WrapOperation(method = "pushEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"))
	private AABB pehkui$tickCramming$getBoundingBox(LivingEntity obj, Operation<AABB> original)
	{
		final AABB bounds = original.call(obj);
		
		final float interactionWidth = ScaleUtils.getInteractionBoxWidthScale(obj);
		final float interactionHeight = ScaleUtils.getInteractionBoxHeightScale(obj);
		
		if (interactionWidth != 1.0F || interactionHeight != 1.0F)
		{
			final double scaledXLength = bounds.getXsize() * 0.5D * (interactionWidth - 1.0F);
			final double scaledYLength = bounds.getYsize() * 0.5D * (interactionHeight - 1.0F);
			final double scaledZLength = bounds.getZsize() * 0.5D * (interactionWidth - 1.0F);
			
			return bounds.inflate(scaledXLength, scaledYLength, scaledZLength);
		}
		
		return bounds;
	}
}
