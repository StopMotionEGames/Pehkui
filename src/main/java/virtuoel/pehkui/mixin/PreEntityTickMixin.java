package virtuoel.pehkui.mixin;

import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;
import virtuoel.pehkui.mixin.world.entity.EntityExtensionsMixin;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin({
	AbstractMinecart.class,
	EndCrystal.class,
	FallingBlockEntity.class,
	PrimedTnt.class,
	BlockAttachedEntity.class
})
public abstract class PreEntityTickMixin extends EntityExtensionsMixin {
	@Inject(at = @At("HEAD"), method = "tick")
	private void pehkui$tick(CallbackInfo info) {
		for (final ScaleType scaleType : ScaleRegistries.SCALE_TYPES.values()) {
			ScaleUtils.tickScale(pehkui_getScaleData(scaleType));
		}
	}
}
