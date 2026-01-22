package virtuoel.pehkui.mixin.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import virtuoel.pehkui.util.PehkuiEntityRenderStateExtensions;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements PehkuiEntityRenderStateExtensions {

	@Shadow
	public double z;
	@Unique
	private float pehkui$modelWidthScale = 1.0f;
	@Unique
	private float pehkui$modelHeightScale = 1.0f;

	@Unique
	private float pehkui$boundingBoxWidthScale = 1.0f;

	@Unique
	private float pehkui$boundingBoxHeightScale = 1.0f;

	private float pehkui$interactionBoxWidthScale = 1.0f;

	private float pehkui$interactionBoxHeightScale = 1.0f;

	private float pehkui$targetingMargin = 0.0f;

	private AABB pehkui$currentBoundingBox = new AABB(0, 0, 0, 0, 0, 0);

	@Override
	public float pehkui$getModelWidthScale() {
		return pehkui$modelWidthScale;
	}

	@Override
	public float pehkui$getModelHeighScale() {
		return pehkui$modelHeightScale;
	}

	@Override
	public void pehkui$setModelHeightScale(float scale) {
		pehkui$modelHeightScale = scale;
	}

	@Override
	public void pehkui$setModelWidthScale(float scale) {
		pehkui$modelWidthScale = scale;
	}

	@Override
	public float pehkui$getBoundingBoxWidthScale() {
		return pehkui$boundingBoxWidthScale;
	}

	@Override
	public float pehkui$getBoundingBoxHeightScale() {
		return pehkui$boundingBoxHeightScale;
	}

	@Override
	public void pehkui$setBoundingBoxWidthScale(float scale) {
		pehkui$boundingBoxWidthScale = scale;
	}

	@Override
	public void pehkui$setBoundingBoxHeightScale(float scale) {
		pehkui$boundingBoxHeightScale = scale;
	}

	@Override
	public float pehkui$getInteractionBoxWidthScale() {
		return pehkui$interactionBoxWidthScale;
	}

	@Override
	public float pehkui$getInteractionBoxHeightScale() {
		return pehkui$interactionBoxHeightScale;
	}

	@Override
	public void pehkui$setInteractionBoxWidthScale(float scale) {
		pehkui$interactionBoxWidthScale = scale;
	}

	@Override
	public void pehkui$setInteractionBoxHeightScale(float scale) {
		pehkui$interactionBoxHeightScale = scale;
	}

	@Override
	public float pehkui$getTargetingMargin() {
		return pehkui$targetingMargin;
	}

	@Override
	public void pehkui$setTargetingMargin(float targetingMargin) {
		pehkui$targetingMargin = targetingMargin;
	}

	@Override
	public AABB pehkui$getCurrentBoundingBox() {
		return pehkui$currentBoundingBox;
	}

	@Override
	public void pehkui$setCurrentBoundingBox(AABB box) {
		pehkui$currentBoundingBox = box;
	}
}
