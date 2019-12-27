package justfatlard.quartz_tools;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;

public class QuartzAxeItem extends AxeItem {
	public QuartzAxeItem() {
		super(QuartzTools.QUARTZ_TOOL_MATERIAL, 3, -3, new Settings().maxCount(1).group(ItemGroup.TOOLS));
	}
}
