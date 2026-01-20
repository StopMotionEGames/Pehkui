package virtuoel.pehkui.mixin.reach.compat1204minus.compat119plus;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin
{
	@Shadow @Final @Mutable
	ServerPlayer player;
	
	@Dynamic
	@WrapOperation(method = "handleBlockBreakAction", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = MixinConstants.MAX_BREAK_SQUARED_DISTANCE))
	private double pehkui$processBlockBreakingAction$distance(Operation<Double> original)
	{
		final float scale = ScaleUtils.getBlockReachScale(player);
		return scale <= 1.0F ? original.call() : original.call() * scale * scale;
	}
}
