package virtuoel.pehkui.mixin.compat1215plus;

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
		// Na 1.21.5, verificamos se a chave existe e se o elemento é um IntArray de tamanho 4
		CompoundTag self = (CompoundTag) (Object) this;
		return self.get(key) instanceof net.minecraft.nbt.IntArrayTag array && array.size() == 4;
	}

	@Override
	public UUID pehkui_getUuid(String key)
	{
		CompoundTag self = (CompoundTag) (Object) this;

		// A forma "moderna" (1.21.5+) usa o sistema de Codecs do Minecraft
		// O Uuids.INT_STREAM_CODEC é o padrão para UUIDs em NBT (4 ints)
		return self.read(key, net.minecraft.core.UUIDUtil.CODEC).orElse(null);
	}
}
