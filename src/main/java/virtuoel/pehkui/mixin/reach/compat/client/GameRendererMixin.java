package virtuoel.pehkui.mixin.reach.compat.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import virtuoel.pehkui.util.ReachEntityAttributesCompatibility;
import virtuoel.pehkui.util.ScaleRenderUtils;

@Mixin(value = GameRenderer.class, priority = 990)
public class GameRendererMixin
{
	@Shadow @Final @Mutable
	Minecraft minecraft;
	
	@ModifyVariable(method = "pick", ordinal = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyePosition(F)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$updateCrosshairTarget$setDistance(double value, float tickProgress)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			if (!ScaleRenderUtils.hasExtendedReach(minecraft.gameMode))
			{
				final double baseEntityReach = minecraft.gameMode.getPlayerMode().isCreative() ? 5.0F : 4.5F;
				
				return ReachEntityAttributesCompatibility.INSTANCE.getAttackRange(minecraft.player, baseEntityReach);
			}
		}
		
		return value;
	}
	
	@ModifyVariable(method = "pick", ordinal = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$updateCrosshairTarget$fixDistance(double value, float tickProgress)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			if (ScaleRenderUtils.hasExtendedReach(minecraft.gameMode))
			{
				return ReachEntityAttributesCompatibility.INSTANCE.getAttackRange(minecraft.player, 6.0D);
			}
		}
		
		return value;
	}
	
	@ModifyVariable(method = "pick", ordinal = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"))
	private double pehkui$updateCrosshairTarget$fixSquaredDistance(double value, float tickProgress)
	{
		final Entity entity = minecraft.getCameraEntity();
		
		if (entity != null)
		{
			if (this.minecraft.hitResult == null || this.minecraft.hitResult.getType() == HitResult.Type.MISS)
			{
				final double baseEntityReach = ScaleRenderUtils.hasExtendedReach(minecraft.gameMode) ? 6.0D : minecraft.gameMode.getPlayerMode().isCreative() ? 5.0F : 4.5F;
				final double entityReach = ReachEntityAttributesCompatibility.INSTANCE.getAttackRange(minecraft.player, baseEntityReach);

				return entityReach * entityReach;
			}
		}
		
		return value;
	}
}
