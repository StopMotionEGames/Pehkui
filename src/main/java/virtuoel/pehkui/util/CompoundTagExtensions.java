package virtuoel.pehkui.util;

import java.util.UUID;

public interface CompoundTagExtensions
{
	boolean pehkui_containsUuid(String key);
	UUID pehkui_getUuid(String key);
}
