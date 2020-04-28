package justfatlard.quartz_tools;

import net.minecraft.block.Blocks;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class QuartzToolMaterial implements ToolMaterial {
	@Override
	public int getMiningLevel(){ return 3; }

	@Override
	public int getDurability(){ return 128; }

	@Override
	public float getMiningSpeed(){ return 10.0f; }

	@Override
	public float getAttackDamage(){ return 4.0F; }

	@Override
	public int getEnchantability(){ return 30; }

	@Override
	public Ingredient getRepairIngredient(){ return Ingredient.ofItems(Blocks.SMOOTH_QUARTZ); }
}
