package virtuoel.pehkui.mixin.client.gui.screens.inventory;

import java.util.Map;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.util.ReflectionUtils;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {
	@Unique
	private static final ThreadLocal<Map<ScaleType, ScaleData>> pehkui$SCALES = ThreadLocal.withInitial(Object2ObjectLinkedOpenHashMap::new);
	@Unique
	private static final ScaleData pehkui$IDENTITY = ScaleData.Builder.create().build();

	@Inject(method = "renderEntityInInventory", at = @At(value = "HEAD"))
	private static void pehkui$drawEntity$head(GuiGraphics guiGraphics, int i, int j, int k, int l, float f, Vector3f vector3f, Quaternionf quaternionf, Quaternionf quaternionf2, LivingEntity livingEntity, CallbackInfo ci, @Share("bounds") LocalRef<AABB> bounds) {
		final Map<ScaleType, ScaleData> scales = pehkui$SCALES.get();

		ScaleData data;
		ScaleData cachedData;
		for (final ScaleType type : ScaleRegistries.SCALE_TYPES.values()) {
			cachedData = scales.computeIfAbsent(type, t -> ScaleData.Builder.create().type(t).build());
			data = type.getScaleData(livingEntity);
			cachedData.fromScale(data, false);
			data.fromScale(pehkui$IDENTITY, false);
		}

		bounds.set(livingEntity.getBoundingBox());

		final EntityDimensions dims = livingEntity.getDimensions(livingEntity.getPose());
		final Vec3 pos = livingEntity.position();
		final double r = ReflectionUtils.getDimensionsWidth(dims) / 2.0D;
		final double h = ReflectionUtils.getDimensionsHeight(dims);
		final double xPos = pos.x;
		final double yPos = pos.y;
		final double zPos = pos.z;
		final AABB box = new AABB(xPos - r, yPos, zPos - r, xPos + r, yPos + h, zPos + r);

		livingEntity.setBoundingBox(box);
	}

	@Inject(method = "renderEntityInInventory", at = @At(value = "RETURN"))
	private static void pehkui$drawEntity$return(GuiGraphics guiGraphics, int i, int j, int k, int l, float f, Vector3f vector3f, Quaternionf quaternionf, Quaternionf quaternionf2, LivingEntity livingEntity, CallbackInfo ci, @Share("bounds") LocalRef<AABB> bounds) {
		final Map<ScaleType, ScaleData> scales = pehkui$SCALES.get();

		for (final ScaleType type : ScaleRegistries.SCALE_TYPES.values()) {
			type.getScaleData(livingEntity).fromScale(scales.get(type), false);
		}

		livingEntity.setBoundingBox(bounds.get());
	}

	@WrapOperation(method = "renderEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphics;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getBbHeight()F"))
	private static float pehkui$drawEntity$getHeight(LivingEntity obj, Operation<Float> original) {
		final float value = original.call(obj);
		final float scale = ScaleUtils.getBoundingBoxHeightScale(obj);

		return scale != 1.0F ? ScaleUtils.divideClamped(value, scale) : value;
	}
}
