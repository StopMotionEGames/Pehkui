package virtuoel.pehkui.mixin.nbt;

import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import virtuoel.pehkui.util.CompoundTagExtensions;

@Mixin(CompoundTag.class)
public abstract class CompoundTagMixin implements CompoundTagExtensions {
	@Shadow
	abstract boolean hasUUID(String key);

	@Shadow
	abstract UUID getUUID(String key);

	@Override
	public boolean pehkui_containsUuid(String key) {
		return hasUUID(key);
	}

	@Override
	public UUID pehkui_getUuid(String key) {
		return getUUID(key);
	}
}
