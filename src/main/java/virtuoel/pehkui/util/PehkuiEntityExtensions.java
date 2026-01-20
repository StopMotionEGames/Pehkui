package virtuoel.pehkui.util;

import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleType;

public interface PehkuiEntityExtensions
{
	ScaleData pehkui_constructScaleData(ScaleType type);
	
	ScaleData pehkui_getScaleData(ScaleType type);
	
	ScaleData[] pehkui_getScaleCache();
	
	void pehkui_setScaleCache(ScaleData[] scaleCache);
	
	Map<ScaleType, ScaleData> pehkui_getScales();
	
	boolean pehkui_shouldSyncScales();
	
	void pehkui_setShouldSyncScales(boolean sync);
	
	boolean pehkui_shouldIgnoreScaleNbt();
	
	void pehkui_setShouldIgnoreScaleNbt(boolean ignore);
	
	void pehkui_readScaleNbt(ValueInput view);
	
	CompoundTag pehkui_writeScaleNbt(ValueOutput view);
	
	boolean pehkui_isFirstUpdate();
	
	boolean pehkui_getOnGround();
	
	void pehkui_setOnGround(boolean onGround);
}
