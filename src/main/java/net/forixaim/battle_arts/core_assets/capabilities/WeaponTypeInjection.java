package net.forixaim.battle_arts.core_assets.capabilities;

import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.*;

public class WeaponTypeInjection
{

	public static void inject()
	{
		UchigatanaAttacks.inject();
		SwordAttacks.loadAttacks();
		SpearAttacks.loadAttacks();
		LongswordAttacks.loadAttacks();
		GreatswordAttacks.loadAttacks();
	}

}
