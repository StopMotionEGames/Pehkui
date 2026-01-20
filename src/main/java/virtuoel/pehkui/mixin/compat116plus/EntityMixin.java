package virtuoel.pehkui.mixin.compat116plus;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
public abstract class EntityMixin
{
	@Shadow
	private BlockPos blockPosition;
	
	@Unique
	protected void setPosDirectly(final BlockPos pos)
	{
		blockPosition = pos;
	}
}
