package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles;

import yesman.epicfight.world.capabilities.item.Style;

public enum HeroStyles implements Style
{
	HERO_SWORD(true),
	HERO_SWORD_SHIELD(false),
	HERO_HAND_AXE(false),
	HERO_BATTLE_AXE(false);

	final boolean OffHandUse;
	final int id;
	HeroStyles(boolean OffHandUse)
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
