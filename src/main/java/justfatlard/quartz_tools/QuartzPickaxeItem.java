package justfatlard.quartz_tools;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;

public class QuartzPickaxeItem extends PickaxeItem {
	public QuartzPickaxeItem() {
		super(Main.QUARTZ_TOOL_MATERIAL, 1, -2.8F, new Settings().maxCount(1).group(ItemGroup.TOOLS));
	}
}
