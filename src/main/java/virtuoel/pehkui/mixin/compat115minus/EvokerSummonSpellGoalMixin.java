package virtuoel.pehkui.mixin.compat115minus;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(targets = "net.minecraft.world.entity.monster.illager.Evoker$EvokerSummonSpellGoal")
public class EvokerSummonSpellGoalMixin
{
	@Dynamic
	@ModifyArg(method = MixinConstants.CAST_SPELL, at = @At(value = "INVOKE", target = MixinConstants.SPAWN_ENTITY))
	private Entity pehkui$castSpell$spawnEntity(Entity entity)
	{
		if (entity instanceof Vex)
		{
			ScaleUtils.loadScale(entity, ((Vex) entity).getOwner());
		}
		
		return entity;
	}
}
