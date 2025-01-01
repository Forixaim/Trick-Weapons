package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.SquireWieldStyles;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.SwordType;

import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.squire.SquireMoveSet.SquireSwordMS;
import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.novice.SquireProviders.SQUIRE_SWORD_STYLE_CHECK;

public class SwordAttacks
{

	public static void loadAttacks()
	{
		SwordType.getInstance().getStyleComboProviderRegistry().add(SQUIRE_SWORD_STYLE_CHECK);

		SwordType.getInstance().getAttackSets().put(
				SquireWieldStyles.SQUIRE_SWORD,
				SquireSwordMS
		);
	}



}
