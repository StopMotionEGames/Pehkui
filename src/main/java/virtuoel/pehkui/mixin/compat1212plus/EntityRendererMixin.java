package virtuoel.pehkui.mixin.compat1212plus;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;
import virtuoel.pehkui.util.ScaleUtils;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
	@Inject(method = "extractRenderState", at=@At("RETURN"))
	private void pehkui$copyScale(T entity, S state, float tickProgress, CallbackInfo ci){
		PehkuiEntityRenderStateExtensions ext = (PehkuiEntityRenderStateExtensions) state;

		ext.pehkui$setModelWidthScale(ScaleUtils.getModelWidthScale(entity, tickProgress));
		ext.pehkui$setModelHeightScale(ScaleUtils.getModelHeightScale(entity, tickProgress));
		ext.pehkui$setBoundingBoxWidthScale(ScaleUtils.getBoundingBoxWidthScale(entity, tickProgress));
		ext.pehkui$setBoundingBoxHeightScale(ScaleUtils.getBoundingBoxHeightScale(entity, tickProgress));
		ext.pehkui$setInteractionBoxWidthScale(ScaleUtils.getBoundingBoxWidthScale(entity,tickProgress));
		ext.pehkui$setInteractionBoxHeightScale(ScaleUtils.getBoundingBoxHeightScale(entity,tickProgress));
		ext.pehkui$setTargetingMargin(entity.getPickRadius());
		ext.pehkui$setCurrentBoundingBox(entity.getBoundingBox());
	}
}
