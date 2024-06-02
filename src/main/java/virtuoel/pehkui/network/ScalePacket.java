package virtuoel.pehkui.network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.util.ScaleUtils;

public class ScalePacket
{
	public final int entityId;
	public final Collection<ScaleData> scales = new ArrayList<>();
	public final Map<Identifier, NbtCompound> syncedScales = new HashMap<>();
	
	public ScalePacket(final Entity entity, final Collection<ScaleData> scales)
	{
		entityId = entity.getEntityId();
		this.scales.addAll(scales);
		
		for (final ScaleData s : scales)
		{
			final Identifier typeId = ScaleRegistries.getId(ScaleRegistries.SCALE_TYPES, s.getScaleType());
			
			syncedScales.put(typeId, s.writeNbt(new NbtCompound()));
		}
	}
	
	public ScalePacket(final PacketByteBuf buf)
	{
		entityId = buf.readVarInt();
		
		for (int i = buf.readInt(); i > 0; i--)
		{
			final Identifier typeId = buf.readIdentifier();
			
			final NbtCompound scaleData = ScaleUtils.buildScaleNbtFromPacketByteBuf(buf);
			
			syncedScales.put(typeId, scaleData);
		}
	}
	
	public void write(final PacketByteBuf buf)
	{
		buf.writeVarInt(entityId);
		((ByteBuf) buf).writeInt(scales.size());
		
		for (final ScaleData s : scales)
		{
			buf.writeIdentifier(ScaleRegistries.getId(ScaleRegistries.SCALE_TYPES, s.getScaleType()));
			s.toPacket(buf);
		}
	}
}
