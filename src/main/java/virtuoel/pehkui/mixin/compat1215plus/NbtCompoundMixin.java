package virtuoel.pehkui.mixin.compat1215plus;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.nbt.NbtCompound;
import virtuoel.pehkui.util.NbtCompoundExtensions;

@Mixin(NbtCompound.class)
public abstract class NbtCompoundMixin implements NbtCompoundExtensions
{
	@Override
	public boolean pehkui_containsUuid(String key)
	{
		NbtCompound self = (NbtCompound) (Object) this;
		return self.get(key) instanceof net.minecraft.nbt.NbtIntArray array && array.size() == 4;
	}

	@Override
	public UUID pehkui_getUuid(String key)
	{
		NbtCompound self = (NbtCompound) (Object) this;
		return self.get(key, net.minecraft.util.Uuids.INT_STREAM_CODEC).orElse(null);
	}
}
