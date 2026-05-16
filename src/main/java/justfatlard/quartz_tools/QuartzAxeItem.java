package justfatlard.quartz_tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class QuartzAxeItem extends Item {
	public QuartzAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties settings) {
		super(settings.axe(material, attackDamage, attackSpeed));
	}
}
