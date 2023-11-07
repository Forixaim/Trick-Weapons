package net.forixaim.trick_weapons.bridgeassets;

import yesman.epicfight.world.capabilities.item.Style;

public enum TrickWeaponsStyles implements Style
{
	/**
	 * Rapiers are light and thin swords that are used for consecutive light quick thrust attacks with some minor slashes.
	 */
	RAPIER(false),
	/**
	 * Chakrams are small and lightweight throwing weapons that excel in ranged combat but can miss on close range encounters.
	 */
	CHAKRAM(true),
	/**
	 * Imagine the stupid range of chakrams, but double it.
	 */
	DUAL_CHAKRAMS(false);


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
