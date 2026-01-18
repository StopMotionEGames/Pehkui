package virtuoel.pehkui.util;

import net.minecraft.util.math.Box;

public interface PehkuiEntityRenderStateExtensions {
	float pehkui$getModelWidthScale();

	float pehkui$getModelHeighScale();

	void pehkui$setModelHeightScale(float scale);

	void pehkui$setModelWidthScale(float scale);

	float pehkui$getBoundingBoxWidthScale();

	float pehkui$getBoundingBoxHeightScale();

	void pehkui$setBoundingBoxWidthScale(float scale);

	void pehkui$setBoundingBoxHeightScale(float scale);

	float pehkui$getInteractionBoxWidthScale();

	float pehkui$getInteractionBoxHeightScale();

	void pehkui$setInteractionBoxWidthScale(float scale);

	void pehkui$setInteractionBoxHeightScale(float scale);

	float pehkui$getTargetingMargin();

	void pehkui$setTargetingMargin(float targetingMargin);

	Box pehkui$getCurrentBoundingBox();

	void pehkui$setCurrentBoundingBox(Box box);
}
