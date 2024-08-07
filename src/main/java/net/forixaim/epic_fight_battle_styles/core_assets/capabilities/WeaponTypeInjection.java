package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_attacks.JoyeuseAttacks;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_attacks.LongswordAttacks;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_attacks.UchigatanaAttacks;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_types.JoyeuseType;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_types.UchigatanaType;

public class WeaponTypeInjection
{

	public static void inject()
	{
		LongswordAttacks.injectAttacks();
		JoyeuseAttacks.injectAttacks();
		UchigatanaAttacks.inject();
	}

}
