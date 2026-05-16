package justfatlard.quartz_tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class QuartzPickaxeItem extends Item {
	public QuartzPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties settings) {
		super(settings.pickaxe(material, attackDamage, attackSpeed));
	}
}
