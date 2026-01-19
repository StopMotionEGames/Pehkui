package virtuoel.pehkui.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin
{
	// todo: see if knockback is ok
	@ModifyArg(method = "tryAttack", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;knockbackTarget(Lnet/minecraft/entity/Entity;FLnet/minecraft/util/math/Vec3d;)V"))
	private float pehkui$tryAttack$knockback(float strength)
	{
		final float scale = ScaleUtils.getKnockbackScale((Entity) (Object) this);
		
		return scale != 1.0F ? scale * strength : strength;
	}
	
	@WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Box;expand(DDD)Lnet/minecraft/util/math/Box;"))
	private Box pehkui$tickMovement$expand(Box obj, double x, double y, double z, Operation<Box> original)
	{
		Entity entity = (Entity) (Object) this;
		final float widthScale = ScaleUtils.getBoundingBoxWidthScale(entity);
		final float heightScale = ScaleUtils.getBoundingBoxHeightScale(entity);
		
		if (widthScale != 1.0F)
		{
			x *= widthScale;
			z *= widthScale;
		}
		
		if (heightScale != 1.0F)
		{
			y *= heightScale;
		}
		
		return original.call(obj, x, y, z);
	}
}
