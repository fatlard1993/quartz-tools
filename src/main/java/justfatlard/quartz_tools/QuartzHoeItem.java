package justfatlard.quartz_tools;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemGroup;

public class QuartzHoeItem extends HoeItem {
	public QuartzHoeItem() {
		super(QuartzTools.QUARTZ_TOOL_MATERIAL, 0, new Settings().maxCount(1).group(ItemGroup.TOOLS));
	}
}
