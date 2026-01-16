package virtuoel.pehkui.mixin.compat1212plus;

import net.minecraft.entity.conversion.EntityConversionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(MobEntity.class)
public class MobEntityMixin
{
	@Inject(at = @At("RETURN"), method = "convertTo(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/conversion/EntityConversionContext;Lnet/minecraft/entity/conversion/EntityConversionContext$Finalizer;)Lnet/minecraft/entity/mob/MobEntity;")
	private <T extends MobEntity> void pehkui$convertTo(EntityType<T> entityType, EntityConversionContext context, EntityConversionContext.Finalizer<T> finalizer, CallbackInfoReturnable<T> cir)
	{
		final MobEntity e = cir.getReturnValue();
		
		if (e != null)
		{
			ScaleUtils.loadScale(e, (Entity) (Object) this);
		}
	}
}
