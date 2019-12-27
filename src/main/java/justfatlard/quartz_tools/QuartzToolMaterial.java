package justfatlard.quartz_tools;

import net.minecraft.block.Blocks;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class QuartzToolMaterial implements ToolMaterial {
	@Override
	public int getDurability() {
		return 200;
	}

	@Override
	public float getAttackDamage() {
		return 5.5F;
	}

	@Override
	public float getMiningSpeed() {
		return 6.5f;
	}

	@Override
	public int getMiningLevel() {
		return 2;
	}

	@Override
	public int getEnchantability() {
		return 22;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(Blocks.SMOOTH_QUARTZ);
	}
}
