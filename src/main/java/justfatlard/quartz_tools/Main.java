package justfatlard.quartz_tools;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.item.ItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class Main implements ModInitializer {
	public static final String MOD_ID = "quartz-tools-justfatlard";

	// Quartz tool stats - high power, low durability, high enchantability
	public static final TagKey<Block> INCORRECT_FOR_QUARTZ_TOOL = BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
	public static final int DURABILITY = 128;
	public static final float MINING_SPEED = 10.0f;
	public static final float ATTACK_DAMAGE_BONUS = 4.0f;
	public static final int ENCHANTABILITY = 30;
	public static final TagKey<Item> REPAIR_ITEMS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "quartz_tool_repair_items"));

	public static final ToolMaterial QUARTZ_TOOL_MATERIAL = new ToolMaterial(
		INCORRECT_FOR_QUARTZ_TOOL,
		DURABILITY,
		MINING_SPEED,
		ATTACK_DAMAGE_BONUS,
		ENCHANTABILITY,
		REPAIR_ITEMS
	);

	private static RegistryKey<Item> keyOf(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
	}

	// Tool items
	public static final Item QUARTZ_PICKAXE = new QuartzPickaxeItem(
		QUARTZ_TOOL_MATERIAL, 1.0f, -2.8f,
		new Item.Settings().registryKey(keyOf("quartz_pickaxe")).maxCount(1)
	);

	public static final Item QUARTZ_AXE = new QuartzAxeItem(
		QUARTZ_TOOL_MATERIAL, 5.0f, -3.0f,
		new Item.Settings().registryKey(keyOf("quartz_axe")).maxCount(1)
	);

	public static final Item QUARTZ_SHOVEL = new QuartzShovelItem(
		QUARTZ_TOOL_MATERIAL, 1.5f, -3.0f,
		new Item.Settings().registryKey(keyOf("quartz_shovel")).maxCount(1)
	);

	public static final Item QUARTZ_HOE = new QuartzHoeItem(
		QUARTZ_TOOL_MATERIAL, -3.0f, 0.0f,
		new Item.Settings().registryKey(keyOf("quartz_hoe")).maxCount(1)
	);

	public static final Item QUARTZ_SWORD = new QuartzSwordItem(
		QUARTZ_TOOL_MATERIAL, 3.0f, -2.4f,
		new Item.Settings().registryKey(keyOf("quartz_sword")).maxCount(1)
	);

	@Override
	public void onInitialize() {
		PolymerResourcePackUtils.addModAssets(MOD_ID);
		PolymerResourcePackUtils.markAsRequired();

		Registry.register(Registries.ITEM, keyOf("quartz_pickaxe"), QUARTZ_PICKAXE);
		Registry.register(Registries.ITEM, keyOf("quartz_axe"), QUARTZ_AXE);
		Registry.register(Registries.ITEM, keyOf("quartz_shovel"), QUARTZ_SHOVEL);
		Registry.register(Registries.ITEM, keyOf("quartz_hoe"), QUARTZ_HOE);
		Registry.register(Registries.ITEM, keyOf("quartz_sword"), QUARTZ_SWORD);

		ItemGroup quartzToolsGroup = PolymerItemGroupUtils.builder()
			.displayName(Text.literal("Quartz Tools"))
			.icon(() -> new ItemStack(QUARTZ_PICKAXE))
			.entries((context, entries) -> {
				entries.add(new ItemStack(QUARTZ_SWORD));
				entries.add(new ItemStack(QUARTZ_PICKAXE));
				entries.add(new ItemStack(QUARTZ_AXE));
				entries.add(new ItemStack(QUARTZ_SHOVEL));
				entries.add(new ItemStack(QUARTZ_HOE));
			})
			.build();
		PolymerItemGroupUtils.registerPolymerItemGroup(Identifier.of(MOD_ID, "quartz_tools"), quartzToolsGroup);

		// Add tools to vanilla Tools creative tab
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
			entries.add(QUARTZ_PICKAXE);
			entries.add(QUARTZ_AXE);
			entries.add(QUARTZ_SHOVEL);
			entries.add(QUARTZ_HOE);
		});

		// Add sword to vanilla Combat creative tab
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.add(QUARTZ_SWORD);
		});

		System.out.println("[quartz-tools] Loaded quartz-tools (server-side with Polymer)");
	}
}
