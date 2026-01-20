package virtuoel.pehkui.mixin.compat115minus;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.villager.Villager;
import virtuoel.pehkui.util.MixinConstants;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Villager.class)
public class VillagerMixin
{
	@Dynamic
	@Inject(method = MixinConstants.ON_STRUCK_BY_LIGHTNING, at = @At(value = "INVOKE", shift = Shift.BEFORE, target = MixinConstants.WITCH_REFRESH_POS_AND_ANGLES))
	private void pehkui$onStruckByLightning(LightningBolt lightning, CallbackInfo info, @Local Witch witchEntity)
	{
		ScaleUtils.loadScale(witchEntity, (Entity) (Object) this);
	}
}
