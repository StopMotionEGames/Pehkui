package virtuoel.pehkui.mixin.compat1206minus;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractHurtingProjectile.class)
public abstract class ExplosiveProjectileEntityMixin
{
	@Dynamic
	@Shadow
	public double field_7601; // UNMAPPED_FIELD
	@Dynamic
	@Shadow
	public double field_7600; // UNMAPPED_FIELD
	@Dynamic
	@Shadow
	public double field_7599; // UNMAPPED_FIELD
	
	@Dynamic
	@Inject(at = @At("RETURN"), method = MixinConstants.EXPLOSIVE_PROJECTILE_ENTITY_INIT)
	private void pehkui$construct(EntityType<? extends AbstractHurtingProjectile> type, LivingEntity owner, double directionX, double directionY, double directionZ, Level world, CallbackInfo info)
	{
		final AbstractHurtingProjectile self = (AbstractHurtingProjectile) (Object) this;
		final float scale = ScaleUtils.setScaleOfProjectile(self, owner);
		
		if (scale != 1.0F)
		{
			field_7601 *= scale;
			field_7600 *= scale;
			field_7599 *= scale;
		}
	}
}
