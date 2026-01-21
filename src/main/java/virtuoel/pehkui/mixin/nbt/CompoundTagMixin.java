package virtuoel.pehkui.mixin.nbt;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import virtuoel.pehkui.util.CompoundTagExtensions;

@Mixin(CompoundTag.class)
public abstract class CompoundTagMixin implements CompoundTagExtensions
{
	@Override
	public boolean pehkui_containsUuid(String key)
	{
		CompoundTag self = (CompoundTag) (Object) this;
		return self.get(key) instanceof net.minecraft.nbt.IntArrayTag array && array.size() == 4;
	}

	@Override
	public UUID pehkui_getUuid(String key)
	{
		CompoundTag self = (CompoundTag) (Object) this;
		return self.read(key, net.minecraft.core.UUIDUtil.CODEC).orElse(null);
	}
}
