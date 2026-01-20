package virtuoel.pehkui.mixin.compat1212plus;

import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Mob.class)
public class MobMixin
{
	@Inject(at = @At("RETURN"), method = "convertTo(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/ConversionParams;Lnet/minecraft/world/entity/ConversionParams$AfterConversion;)Lnet/minecraft/world/entity/Mob;")
	private <T extends Mob> void pehkui$convertTo(EntityType<@NotNull T> entityType, ConversionParams context, ConversionParams.AfterConversion<T> finalizer, CallbackInfoReturnable<T> cir)
	{
		final Mob e = cir.getReturnValue();
		
		if (e != null)
		{
			ScaleUtils.loadScale(e, (Entity) (Object) this);
		}
	}
}
