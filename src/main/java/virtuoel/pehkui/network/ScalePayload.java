package virtuoel.pehkui.network;

import java.util.Collection;
import java.util.function.Supplier;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
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
	
	public static void handle(final ScalePayload msg, final Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
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
			});
		});
		
		ctx.get().setPacketHandled(true);
	}
}
