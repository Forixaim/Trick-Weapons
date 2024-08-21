package net.forixaim.battle_arts.core_assets.capabilities.styles;

import yesman.epicfight.world.capabilities.item.Style;

public enum WoMUniqueStyles implements Style
{
	DEMON(false),
	DEMON_SHEATH(false),
	ATLANTEAN(false),
	WARLORD(false);
	final boolean OffHandUse;
	final int id;
	WoMUniqueStyles(boolean OffHandUse)
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
