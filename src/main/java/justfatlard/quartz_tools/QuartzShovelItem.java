package justfatlard.quartz_tools;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

public class QuartzShovelItem extends Item implements PolymerItem {
	private final Identifier modelId;

	public QuartzShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
		super(settings.shovel(material, attackDamage, attackSpeed));
		this.modelId = Identifier.of(Main.MOD_ID, "quartz_shovel");
	}

	@Override
	public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
		return Items.DIAMOND_SHOVEL;
	}

	@Override
	public Identifier getPolymerItemModel(ItemStack itemStack, PacketContext context) {
		return this.modelId;
	}
}
