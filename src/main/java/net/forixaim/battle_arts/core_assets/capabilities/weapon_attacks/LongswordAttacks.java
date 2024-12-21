package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.SquireWieldStyles;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.LongswordType;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.SwordType;

import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.SwordAttacks.SQUIRE_SWORD_STYLE_CHECK;
import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.SwordAttacks.squireSwordCombo;

public class LongswordAttacks
{
    public static void loadAttacks()
    {
        LongswordType.getInstance().getStyleComboProviderRegistry().add(SQUIRE_SWORD_STYLE_CHECK);

        LongswordType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(
                SquireWieldStyles.SQUIRE_SWORD, squireSwordCombo
        ));
    }
}
