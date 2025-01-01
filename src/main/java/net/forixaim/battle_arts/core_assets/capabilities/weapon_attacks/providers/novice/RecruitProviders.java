package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.novice;

import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.RecruitWieldStyles;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.QuickFunctions;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.NoviceBattleStyles;
import net.forixaim.efm_ex.api.providers.ProviderConditional;

public class RecruitProviders
{
    public static final ProviderConditional RECRUIT_SPEAR_CHECK = QuickFunctions.battleStyleCheck(
            NoviceBattleStyles.RECRUIT,
            RecruitWieldStyles.RECRUIT_SPEAR,
            false
    );
}
