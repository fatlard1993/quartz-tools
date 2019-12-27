package justfatlard.quartz_tools;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

public class QuartzSwordItem extends SwordItem {
	public QuartzSwordItem() {
		super(QuartzTools.QUARTZ_TOOL_MATERIAL, 0, -2.5F, new Settings().maxCount(1).group(ItemGroup.COMBAT));
	}
}
