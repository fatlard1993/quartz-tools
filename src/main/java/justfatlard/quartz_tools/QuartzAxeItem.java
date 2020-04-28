package justfatlard.quartz_tools;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;

public class QuartzAxeItem extends AxeItem {
	public QuartzAxeItem() {
		super(Main.QUARTZ_TOOL_MATERIAL, 5, -3.0F, new Settings().maxCount(1).group(ItemGroup.TOOLS));
	}
}
