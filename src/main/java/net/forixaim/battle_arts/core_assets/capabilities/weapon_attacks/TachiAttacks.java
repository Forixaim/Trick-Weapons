package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.SquireWieldStyles;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.TachiType;

import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.squire.SquireMoveSet.SquireSwordMS;
import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.novice.SquireProviders.SQUIRE_SWORD_STYLE_CHECK;

public class TachiAttacks
{
    public static void loadAttacks()
    {
        TachiType.getInstance().getStyleComboProviderRegistry().add(SQUIRE_SWORD_STYLE_CHECK);

        TachiType.getInstance().getAttackSets().put(
                SquireWieldStyles.SQUIRE_SWORD,
                SquireSwordMS
        );
    }
}
