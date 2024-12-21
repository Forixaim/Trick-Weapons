package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.SquireWieldStyles;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.GreatswordType;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.LongswordType;

import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.SwordAttacks.SQUIRE_SWORD_STYLE_CHECK;
import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.SwordAttacks.squireSwordCombo;

public class GreatswordAttacks
{
    public static void loadAttacks()
    {
        GreatswordType.getInstance().getStyleComboProviderRegistry().add(SQUIRE_SWORD_STYLE_CHECK);

        GreatswordType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(
                SquireWieldStyles.SQUIRE_SWORD, squireSwordCombo
        ));
    }
}
