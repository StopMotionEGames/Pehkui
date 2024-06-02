package virtuoel.pehkui.network;

import java.util.Collection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;

public class ScalePayload extends ScalePacket implements CustomPayload
{
	public static final CustomPayload.Id<ScalePayload> ID = new CustomPayload.Id<>(Pehkui.SCALE_PACKET);
	public static final PacketCodec<PacketByteBuf, ScalePayload> CODEC = codec(ID);
	
	public ScalePayload(final Entity entity, final Collection<ScaleData> scales)
	{
		super(entity, scales);
	}
	
	public ScalePayload(final PacketByteBuf buf)
	{
		super(buf);
	}
	
	@Override
	public CustomPayload.Id<? extends CustomPayload> getId()
	{
		return ID;
	}
	
	private static PacketCodec<PacketByteBuf, ScalePayload> codec(final CustomPayload.Id<ScalePayload> id)
	{
		return CustomPayload.codecOf(ScalePayload::write, ScalePayload::new);
	}
	
	public static void handle(final ScalePayload msg, final IPayloadContext ctx)
	{
		if (FMLEnvironment.dist == Dist.CLIENT)
		{
			final MinecraftClient client = MinecraftClient.getInstance();
			final Entity entity = client.world.getEntityById(msg.entityId);
			
			if (entity != null)
			{
				msg.syncedScales.forEach((typeId, scaleData) ->
				{
					if (ScaleRegistries.SCALE_TYPES.containsKey(typeId))
					{
						ScaleRegistries.getEntry(ScaleRegistries.SCALE_TYPES, typeId).getScaleData(entity).readNbt(scaleData);
					}
				});
			}
		}
	}
}
