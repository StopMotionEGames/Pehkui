package virtuoel.pehkui.network;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.MixinEnvironment;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import virtuoel.pehkui.server.command.DebugCommand;
import virtuoel.pehkui.util.I18nUtils;

public class DebugPacket
{
	public final DebugCommand.PacketType type;
	
	public DebugPacket(final DebugCommand.PacketType type)
	{
		this.type = type;
	}
	
	public DebugPacket(final PacketByteBuf buf)
	{
		DebugCommand.PacketType read;
		
		try
		{
			read = buf.readEnumConstant(DebugCommand.PacketType.class);
		}
		catch (Exception e)
		{
			read = null;
		}
		
		this.type = read;
	}
	
	public void write(final PacketByteBuf buf)
	{
		buf.writeEnumConstant(this.type);
	}
	
	public static void handle(final DebugPacket msg, final Supplier<NetworkEvent.Context> ctx)
	{
		final DebugCommand.PacketType type = msg.type;
		
		ctx.get().enqueueWork(() ->
		{
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
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
			});
		});
		
		ctx.get().setPacketHandled(true);
	}
}
