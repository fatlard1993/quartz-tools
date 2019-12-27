package justfatlard.quartz_tools;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class QuartzTools implements ModInitializer {
	private final static String MOD_ID = "quartz-tools-justfatlard";

	public final static QuartzToolMaterial QUARTZ_TOOL_MATERIAL = new QuartzToolMaterial();

	public final static QuartzPickaxeItem QUARTZ_PICKAXE_ITEM = new QuartzPickaxeItem();
	public final static QuartzAxeItem QUARTZ_AXE_ITEM = new QuartzAxeItem();
	public final static QuartzShovelItem QUARTZ_SHOVEL_ITEM = new QuartzShovelItem();
	public final static QuartzHoeItem QUARTZ_HOE_ITEM = new QuartzHoeItem();
	public final static QuartzSwordItem QUARTZ_SWORD_ITEM = new QuartzSwordItem();

	@Override
	public void onInitialize(){
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_pickaxe"), QUARTZ_PICKAXE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_axe"), QUARTZ_AXE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_shovel"), QUARTZ_SHOVEL_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_hoe"), QUARTZ_HOE_ITEM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "quartz_sword"), QUARTZ_SWORD_ITEM);
	}
}
