package justfatlard.quartz_tools;

import justfatlard.pandorical.api.ItemRegistration;
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

	private static final ResourceKey<CreativeModeTab> TOOLS_TAB = ResourceKey.create(
		Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("minecraft", "tools_and_utilities")
	);
	private static final ResourceKey<CreativeModeTab> COMBAT_TAB = ResourceKey.create(
		Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("minecraft", "combat")
	);

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
		if (PandoricalApi.isAvailable()) {
			for (String name : new String[] { "quartz_pickaxe", "quartz_axe", "quartz_shovel", "quartz_hoe", "quartz_sword" }) {
				// The kind decides which vanilla helper the client rebuilds it with, and the
				// material carries the numbers. Without this the client's copy is a plain item
				// and every swing is a bare-handed one.
				String kind = name.substring(name.lastIndexOf('_') + 1);
				PandoricalApi.content().registerItem(MOD_ID + ":" + name, new ItemRegistration()
					.model(MOD_ID + ":item/" + name)
					.tool(kind, QUARTZ_TOOL_MATERIAL, attackDamageFor(kind), attackSpeedFor(kind))
					.maxStackSize(1));
			}
			PandoricalApi.content().registerModAssets(MOD_ID);
		}

		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_pickaxe"), QUARTZ_PICKAXE);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_axe"), QUARTZ_AXE);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_shovel"), QUARTZ_SHOVEL);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_hoe"), QUARTZ_HOE);
		Registry.register(BuiltInRegistries.ITEM, keyOf("quartz_sword"), QUARTZ_SWORD);

		CreativeModeTabEvents.modifyOutputEvent(TOOLS_TAB).register(entries -> {
			entries.accept(QUARTZ_PICKAXE);
			entries.accept(QUARTZ_AXE);
			entries.accept(QUARTZ_SHOVEL);
			entries.accept(QUARTZ_HOE);
		});

		CreativeModeTabEvents.modifyOutputEvent(COMBAT_TAB).register(entries -> {
			entries.accept(QUARTZ_SWORD);
		});

		LOGGER.info("Loaded quartz-tools (server-side with Pandorical)");
	}

	/**
	 * The same attack numbers the items above were built with.
	 *
	 * <p>Read from one place by both the real item and the copy the client is told to build, so
	 * the two cannot drift apart the way they had: a tool the client does not know is a tool
	 * swings at hand speed no matter what the server thinks it is holding.
	 */
	private static float attackDamageFor(String kind) {
		return switch (kind) {
			case "axe" -> 5.0F;
			case "shovel" -> 1.5F;
			case "sword" -> 3.0F;
			case "hoe" -> -3.0F;
			default -> 1.0F;
		};
	}

	private static float attackSpeedFor(String kind) {
		return switch (kind) {
			case "sword" -> -2.4F;
			case "pickaxe" -> -2.8F;
			case "hoe" -> 0.0F;
			default -> -3.0F;
		};
	}
}
