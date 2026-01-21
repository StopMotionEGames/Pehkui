package virtuoel.pehkui.mixin.client.renderer;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import virtuoel.pehkui.util.ScaleRenderUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Shadow
	@Final
	@Mutable
	private Minecraft minecraft;

	@Unique
	boolean pehkui$isBobbing = false;

	@ModifyExpressionValue(method = "getProjectionMatrix", at = @At(value = "CONSTANT", args = "floatValue=0.05F"))
	private float pehkui$getProjectionMatrix$depth(float value)
	{
		return ScaleRenderUtils.modifyProjectionMatrixDepth(value, minecraft.getCameraEntity(), ScaleRenderUtils.getTickProgress(minecraft));
	}

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", shift = Shift.BEFORE, target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
	private void pehkui$renderLevel$before(DeltaTracker tickCounter, CallbackInfo info) {
		pehkui$isBobbing = true;
	}

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", shift = Shift.AFTER, target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
	private void pehkui$renderLevel$after(DeltaTracker tickCounter, CallbackInfo info) {
		pehkui$isBobbing = false;
	}

	@WrapOperation(method = "bobView", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
	private void pehkui$bobView$translate(PoseStack obj, float x, float y, float z, Operation<Void> original) {
		if (pehkui$isBobbing) {
			final float scale = ScaleUtils.getViewBobbingScale(minecraft.getCameraEntity(), ScaleRenderUtils.getTickProgress(minecraft));

			if (scale != 1.0F) {
				x *= scale;
				y *= scale;
				z *= scale;
			}
		}

		original.call(obj, x, y, z);
	}

	@ModifyVariable(
		method = "bobView",
		at = @At(value = "STORE"),
		ordinal = 2)
	private float pehkui$bobView$strength(float value, @Local(argsOnly = true) float tickProgress) {
		return value / ScaleUtils.getViewBobbingScale(minecraft.getCameraEntity(), tickProgress);
	}
}
