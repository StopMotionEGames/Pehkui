package virtuoel.pehkui.mixin.compat1206minus.compat1203plus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(AbstractArrow.class)
public abstract class PersistentProjectileEntityMixin
{
	@Dynamic
	@Inject(at = @At("RETURN"), method = MixinConstants.PERSISTENT_PROJECTILE_ENTITY_WITH_STACK_INIT)
	private void pehkui$construct(EntityType<? extends Projectile> type, LivingEntity owner, Level world, ItemStack stack, CallbackInfo info)
	{
		final float scale = ScaleUtils.getEyeHeightScale(owner);
		
		if (scale != 1.0F)
		{
			final Entity self = ((Entity) (Object) this);
			
			final Vec3 pos = self.position();
			
			self.setPos(pos.x, pos.y + ((1.0F - scale) * 0.1D), pos.z);
		}
	}
}
