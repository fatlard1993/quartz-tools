package justfatlard.quartz_tools;

import justfatlard.pandorical.api.PandoricalApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final String MOD_ID = "quartz-tools-justfatlard";
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Quartz tool stats - high power, low durability, high enchantability
	public static final TagKey<Block> INCORRECT_FOR_QUARTZ_TOOL = BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
	public static final int DURABILITY = 128;
	public static final float MINING_SPEED = 10.0f;
	public static final float ATTACK_DAMAGE_BONUS = 4.0f;
	public static final int ENCHANTABILITY = 30;
	public static final TagKey<Item> REPAIR_ITEMS = TagKey.create(
		Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, "quartz_tool_repair_items")
	);

	public static final ToolMaterial QUARTZ_TOOL_MATERIAL = new ToolMaterial(
		INCORRECT_FOR_QUARTZ_TOOL,
		DURABILITY,
		MINING_SPEED,
		ATTACK_DAMAGE_BONUS,
		ENCHANTABILITY,
		REPAIR_ITEMS
	);

	private static ResourceKey<Item> keyOf(String name) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, name));
	}

	// Creative tab keys
	private static final ResourceKey<CreativeModeTab> TOOLS_TAB = ResourceKey.create(
		Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("minecraft", "tools_and_utilities")
	);
	private static final ResourceKey<CreativeModeTab> COMBAT_TAB = ResourceKey.create(
		Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("minecraft", "combat")
	);

	// Tool items
	public static final Item QUARTZ_PICKAXE = new QuartzPickaxeItem(
		QUARTZ_TOOL_MATERIAL, 1.0f, -2.8f,
		new Item.Properties().setId(keyOf("quartz_pickaxe")).stacksTo(1)
	);

	public static final Item QUARTZ_AXE = new QuartzAxeItem(
		QUARTZ_TOOL_MATERIAL, 5.0f, -3.0f,
		new Item.Properties().setId(keyOf("quartz_axe")).stacksTo(1)
	);

	public static final Item QUARTZ_SHOVEL = new QuartzShovelItem(
		QUARTZ_TOOL_MATERIAL, 1.5f, -3.0f,
		new Item.Properties().setId(keyOf("quartz_shovel")).stacksTo(1)
	);

	public static final Item QUARTZ_HOE = new QuartzHoeItem(
		QUARTZ_TOOL_MATERIAL, -3.0f, 0.0f,
		new Item.Properties().setId(keyOf("quartz_hoe")).stacksTo(1)
	);

	public static final Item QUARTZ_SWORD = new QuartzSwordItem(
		QUARTZ_TOOL_MATERIAL, 3.0f, -2.4f,
		new Item.Properties().setId(keyOf("quartz_sword")).stacksTo(1)
	);

	@Override
	public void onInitialize() {
		// Register with Pandorical if available
		if (PandoricalApi.isAvailable()) {
			PandoricalApi.content().registerModAssets(MOD_ID);
		}

		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_pickaxe"), QUARTZ_PICKAXE);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_axe"), QUARTZ_AXE);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_shovel"), QUARTZ_SHOVEL);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_hoe"), QUARTZ_HOE);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_sword"), QUARTZ_SWORD);

		// Add tools to vanilla Tools creative tab
		CreativeModeTabEvents.modifyOutputEvent(TOOLS_TAB).register(entries -> {
			entries.accept(QUARTZ_PICKAXE);
			entries.accept(QUARTZ_AXE);
			entries.accept(QUARTZ_SHOVEL);
			entries.accept(QUARTZ_HOE);
		});

		// Add sword to vanilla Combat creative tab
		CreativeModeTabEvents.modifyOutputEvent(COMBAT_TAB).register(entries -> {
			entries.accept(QUARTZ_SWORD);
		});

		LOGGER.info("Loaded quartz-tools (server-side with Pandorical)");
	}
}
