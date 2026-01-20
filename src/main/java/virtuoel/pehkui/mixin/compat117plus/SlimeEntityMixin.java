package virtuoel.pehkui.mixin.compat117plus;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(Slime.class)
public class SlimeEntityMixin
{
//	@ModifyArg(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
//	private Entity pehkui$remove$spawnEntity(Entity entity)
//	{
//		ScaleUtils.loadScale(entity, (Entity) (Object) this);
//
//		return entity;
//	}
	
	@ModifyExpressionValue(method = "remove", at = @At(value = "CONSTANT", args = "floatValue=0.5F"))
	private float pehkui$remove$verticalOffset(float value)
	{
		final float scale = ScaleUtils.getBoundingBoxHeightScale((Entity) (Object) this);
		
		if (scale != 1.0F)
		{
			return value * scale;
		}
		
		return value;
	}
}

// todo: Test later!
//@Mixin(MobEntity.class)
//public abstract class MobEntityMixin {
//
//    @ModifyArg(
//        method = "convertTo",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
//        )
//    )
//    private Entity pehkui$copyScaleOnSplit(Entity entity) {
//        if ((Object) this instanceof SlimeEntity oldSlime
//            && entity instanceof SlimeEntity newSlime) {
//
//            ScaleUtils.loadScale(newSlime, oldSlime);
//        }
//
//        return entity;
//    }
//}
