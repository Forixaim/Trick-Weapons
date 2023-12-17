package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum BattleStyleCategories implements WeaponCategory
{
	CHAKRAM,
	HAND_AXE,
	BATTLE_AXE;
	final int id;
	BattleStyleCategories() {
		this.id = WeaponCategory.ENUM_MANAGER.assign(this);
	}

	@Override
	public int universalOrdinal()
	{
		return this.id;
	}
}
