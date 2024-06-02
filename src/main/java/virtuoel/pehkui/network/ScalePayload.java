package virtuoel.pehkui.network;

import java.util.Collection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;

public class ScalePayload extends ScalePacket implements CustomPayload
{
	public ScalePayload(final Entity entity, final Collection<ScaleData> scales)
	{
		super(entity, scales);
	}
	
	public ScalePayload(final PacketByteBuf buf)
	{
		super(buf);
	}
	
	@Override
	public void write(final PacketByteBuf buf)
	{
		super.write(buf);
	}
	
	@Override
	public Identifier id()
	{
		return Pehkui.SCALE_PACKET;
	}
	
	public static void handle(final ScalePayload msg, final PlayPayloadContext ctx)
	{
		ctx.workHandler().execute(() ->
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
		});
	}
}
