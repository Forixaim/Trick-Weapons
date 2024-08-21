package net.forixaim.battle_arts.core_assets.capabilities.styles;

import yesman.epicfight.world.capabilities.item.Style;

public enum SamuraiStyles implements Style
{
	SAMURAI_UCHIGATANA(false),
	SAMURAI_UCHIGATANA_SHEATHED(false);
	final boolean offHandUse;
	final int id;
	SamuraiStyles(boolean offHandUse)
	{
		this.offHandUse = offHandUse;
		id = Style.ENUM_MANAGER.assign(this);
	}

	@Override
	public boolean canUseOffhand()
	{
		return offHandUse;
	}

	@Override
	public int universalOrdinal()
	{
		return id;
	}
}
