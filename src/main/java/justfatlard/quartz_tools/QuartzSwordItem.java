package justfatlard.quartz_tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class QuartzSwordItem extends Item {
	public QuartzSwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties settings) {
		super(settings.sword(material, attackDamage, attackSpeed));
	}
}
