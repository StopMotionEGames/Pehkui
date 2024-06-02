package virtuoel.pehkui.mixin.step_height;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.extensions.IEntityExtension;
import virtuoel.pehkui.mixin.PehkuiMixinConfigPlugin;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(value = IEntityExtension.class, priority = 1010)
public interface IForgeEntityMixin
{
	
}
