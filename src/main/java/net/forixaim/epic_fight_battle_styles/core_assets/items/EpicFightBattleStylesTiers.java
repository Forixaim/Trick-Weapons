package net.forixaim.epic_fight_battle_styles.core_assets.items;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum EpicFightBattleStylesTiers implements Tier
{
	ORIGIN_EXCALIBUR(4, 0, 9.0f, 10f, 0, () -> Ingredient.EMPTY);
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyLoadedValue<Ingredient> repairMaterial;

	EpicFightBattleStylesTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		this.harvestLevel = harvestLevelIn;
		this.maxUses = maxUsesIn;
		this.efficiency = efficiencyIn;
		this.attackDamage = attackDamageIn;
		this.enchantability = enchantabilityIn;
		this.repairMaterial = new LazyLoadedValue<>(repairMaterialIn);
	}

	@Override
	public int getUses()
	{
		return this.maxUses;
	}

	@Override
	public float getSpeed()
	{
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus()
	{
		return this.attackDamage;
	}

	@Override
	public int getLevel()
	{
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue()
	{
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return this.repairMaterial.get();
	}
}
