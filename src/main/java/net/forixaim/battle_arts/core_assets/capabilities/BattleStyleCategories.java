package net.forixaim.battle_arts.core_assets.capabilities;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum BattleStyleCategories implements WeaponCategory
{
	CHAKRAM,
	HAND_AXE,
	BATTLE_AXE,
	//Legendary Weapon Categories
	ORIGIN_EXCALIBUR,
	DERIVED_EXCALIBUR,
	ORIGIN_JOYEUSE,
	DERIVED_JOYEUSE;
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
