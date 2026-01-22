package virtuoel.pehkui.mixin.identity.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.util.CombinedScaleData;
import virtuoel.pehkui.util.IdentityCompatibility;
import virtuoel.pehkui.util.PehkuiEntityExtensions;

@Mixin(Player.class)
public abstract class PlayerEntityMixin implements PehkuiEntityExtensions
{
	@Unique
	private static final ScaleData[] pehkui$EMPTY = {};
	
	@Override
	public ScaleData pehkui_constructScaleData(ScaleType type)
	{
		return new CombinedScaleData(type, (Entity) (Object) this, () ->
		{
			final LivingEntity identity = IdentityCompatibility.INSTANCE.getIdentity((Player) (Object) this);
			
			return identity == null ? pehkui$EMPTY : new ScaleData[] { type.getScaleData(identity) };
		});
	}
}
