package virtuoel.pehkui.network;

import org.spongepowered.asm.mixin.MixinEnvironment;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import virtuoel.pehkui.Pehkui;
import virtuoel.pehkui.server.command.DebugCommand;
import virtuoel.pehkui.util.I18nUtils;

public class DebugPayload extends DebugPacket implements CustomPayload
{
	public DebugPayload(final DebugCommand.PacketType type)
	{
		super(type);
	}
	
	public DebugPayload(final PacketByteBuf buf)
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
		return Pehkui.DEBUG_PACKET;
	}
	
	public static void handle(final DebugPayload msg, final PlayPayloadContext ctx)
	{
		final DebugCommand.PacketType type = msg.type;
		
		ctx.workHandler().execute(() ->
		{
			if (FMLEnvironment.dist == Dist.CLIENT)
			{
				final MinecraftClient client = MinecraftClient.getInstance();
				
				switch (type)
				{
					case MIXIN_AUDIT:
						client.player.sendMessage(I18nUtils.translate("commands.pehkui.debug.audit.start.client", "Starting Mixin environment audit (client)..."), false);
						MixinEnvironment.getCurrentEnvironment().audit();
						client.player.sendMessage(I18nUtils.translate("commands.pehkui.debug.audit.end.client", "Mixin environment audit (client) complete!"), false);
						break;
					case GARBAGE_COLLECT:
						System.gc();
						break;
					default:
						break;
				}
			}
		});
	}
}
