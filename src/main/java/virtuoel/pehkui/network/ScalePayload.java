package virtuoel.pehkui.network;

import java.util.Collection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.NetworkEvent;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;

public class ScalePayload extends ScalePacket
{
	public ScalePayload(final Entity entity, final Collection<ScaleData> scales)
	{
		super(entity, scales);
	}
	
	public ScalePayload(final PacketByteBuf buf)
	{
		super(buf);
	}
	
	public static void handle(final ScalePayload msg, final NetworkEvent.Context ctx)
	{
		ctx.enqueueWork(() ->
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
		
		ctx.setPacketHandled(true);
	}
}
