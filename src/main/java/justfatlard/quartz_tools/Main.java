package justfatlard.quartz_tools;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	private final static String MOD_ID = "quartz-tools-justfatlard";

	public final static QuartzToolMaterial QUARTZ_TOOL_MATERIAL = new QuartzToolMaterial();

	public final static QuartzAxeItem QUARTZ_AXE_ITEM = new QuartzAxeItem();
	public final static HoeItem QUARTZ_HOE_ITEM = new HoeItem(Main.QUARTZ_TOOL_MATERIAL, 0, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
	public final static QuartzPickaxeItem QUARTZ_PICKAXE_ITEM = new QuartzPickaxeItem();
	public final static ShovelItem QUARTZ_SHOVEL_ITEM = new ShovelItem(Main.QUARTZ_TOOL_MATERIAL, 1.5F, -3.0F, new Item.Settings().maxCount(1).group(ItemGroup.TOOLS));
	public final static SwordItem QUARTZ_SWORD_ITEM = new SwordItem(Main.QUARTZ_TOOL_MATERIAL, 3, -2.4F, new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));

	@Override
	public void onInitialize(){
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_pickaxe"), QUARTZ_PICKAXE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_axe"), QUARTZ_AXE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_shovel"), QUARTZ_SHOVEL_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_hoe"), QUARTZ_HOE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_sword"), QUARTZ_SWORD_ITEM);
	}
}
