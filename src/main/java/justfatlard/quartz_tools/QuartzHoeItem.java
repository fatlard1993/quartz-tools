package justfatlard.quartz_tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class QuartzHoeItem extends Item {
	public QuartzHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties settings) {
		super(settings.hoe(material, attackDamage, attackSpeed));
	}
}
