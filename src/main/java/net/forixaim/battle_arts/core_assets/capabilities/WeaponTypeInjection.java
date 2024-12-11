package net.forixaim.battle_arts.core_assets.capabilities;

import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.SpearAttacks;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.SwordAttacks;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.UchigatanaAttacks;

public class WeaponTypeInjection
{

	public static void inject()
	{
		UchigatanaAttacks.inject();
		SwordAttacks.loadAttacks();
		SpearAttacks.loadAttacks();
	}

}
