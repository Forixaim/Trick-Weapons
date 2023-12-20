package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles;

import yesman.epicfight.world.capabilities.item.Style;

public enum DuelistStyles implements Style
{
	DUELIST_SWORD(true),
	DUELIST_DUAL_SWORD(true),
	DUELIST_RAPIER(false);

	final boolean OffHandUse;
	final int id;
	DuelistStyles(boolean OffHandUse)
	{
		this.id = ENUM_MANAGER.assign(this);
		this.OffHandUse = OffHandUse;
	}

	@Override
	public int universalOrdinal()
	{
		return this.id;
	}

	@Override
	public boolean canUseOffhand()
	{
		return this.OffHandUse;
	}
}
