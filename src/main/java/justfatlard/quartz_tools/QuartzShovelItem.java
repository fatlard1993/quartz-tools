package justfatlard.quartz_tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class QuartzShovelItem extends Item {
	public QuartzShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties settings) {
		super(settings.shovel(material, attackDamage, attackSpeed));
	}
}
