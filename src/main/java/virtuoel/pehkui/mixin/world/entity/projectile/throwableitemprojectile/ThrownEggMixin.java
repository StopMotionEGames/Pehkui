package virtuoel.pehkui.mixin.world.entity.projectile.throwableitemprojectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEgg;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ThrownEgg.class)
public class ThrownEggMixin
{
	@ModifyArg(method = "onHit(Lnet/minecraft/world/phys/HitResult;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
	private Entity pehkui$onCollision$entity(Entity entity)
	{
		ScaleUtils.loadScale(entity, (Entity) (Object) this);
		
		return entity;
	}
}
