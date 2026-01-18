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
		// Na 1.21.5, verificamos se a chave existe e se o elemento é um IntArray de tamanho 4
		NbtCompound self = (NbtCompound) (Object) this;
		return self.get(key) instanceof net.minecraft.nbt.NbtIntArray array && array.size() == 4;
	}

	@Override
	public UUID pehkui_getUuid(String key)
	{
		NbtCompound self = (NbtCompound) (Object) this;

		// A forma "moderna" (1.21.5+) usa o sistema de Codecs do Minecraft
		// O Uuids.INT_STREAM_CODEC é o padrão para UUIDs em NBT (4 ints)
		return self.get(key, net.minecraft.util.Uuids.INT_STREAM_CODEC).orElse(null);
	}
}
