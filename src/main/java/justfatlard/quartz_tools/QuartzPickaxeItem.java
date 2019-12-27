package justfatlard.quartz_tools;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;

public class QuartzPickaxeItem extends PickaxeItem {
	public QuartzPickaxeItem() {
		super(QuartzTools.QUARTZ_TOOL_MATERIAL, -2, -2.8F, new Settings().maxCount(1).group(ItemGroup.TOOLS));
	}
}
