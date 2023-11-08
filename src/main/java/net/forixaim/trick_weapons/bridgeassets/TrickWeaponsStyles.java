package net.forixaim.trick_weapons.bridgeassets;

import yesman.epicfight.world.capabilities.item.Style;

public enum TrickWeaponsStyles implements Style
{
	;
	final boolean canUseOffHand;
	final int id;

	TrickWeaponsStyles(boolean OffHandUse)
	{
		this.id = Style.ENUM_MANAGER.assign(this);
		this.canUseOffHand = OffHandUse;
	}

	@Override
	public int universalOrdinal()
	{
		return this.id;
	}

	public boolean canUseOffhand()
	{
		return this.canUseOffHand;
	}
}
