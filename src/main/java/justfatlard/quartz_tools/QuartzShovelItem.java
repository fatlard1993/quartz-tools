package justfatlard.quartz_tools;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;

public class QuartzShovelItem extends ShovelItem {
	public QuartzShovelItem() {
		super(QuartzTools.QUARTZ_TOOL_MATERIAL, -2, -3, new Settings().maxCount(1).group(ItemGroup.TOOLS));
	}
}
