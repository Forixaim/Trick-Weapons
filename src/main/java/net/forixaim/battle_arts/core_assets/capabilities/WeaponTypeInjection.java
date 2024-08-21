package net.forixaim.battle_arts.core_assets.capabilities;

import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.JoyeuseAttacks;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.LongswordAttacks;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.UchigatanaAttacks;

public class WeaponTypeInjection
{

	public static void inject()
	{
		LongswordAttacks.injectAttacks();
		JoyeuseAttacks.injectAttacks();
		UchigatanaAttacks.inject();
	}

}
