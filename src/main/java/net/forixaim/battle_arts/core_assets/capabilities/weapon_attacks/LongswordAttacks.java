package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.SquireWieldStyles;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.squire.SquireMoveSet;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.LongswordType;

import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.novice.SquireProviders.SQUIRE_SWORD_STYLE_CHECK;

public class LongswordAttacks
{
    public static void loadAttacks()
    {
        LongswordType.getInstance().getStyleComboProviderRegistry().add(SQUIRE_SWORD_STYLE_CHECK);

        LongswordType.getInstance().getAttackSets().put(SquireWieldStyles.SQUIRE_SWORD, SquireMoveSet.SquireSwordMS);
    }
}
