package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers;

import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillSlots;
import net.forixaim.efm_ex.api.providers.ProviderConditional;
import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.item.Style;

public class QuickFunctions
{
    public static ProviderConditional battleStyleCheck(Skill battleStyle, Style wieldStyle, Boolean visibleOffHand)
    {
        return ProviderConditional.builder()
                .setType(ProviderConditionalType.SKILL_EXISTENCE)
                .setSkillToCheck(battleStyle)
                .setSlot(BattleArtsSkillSlots.BATTLE_STYLE)
                .setWieldStyle(wieldStyle)
                .isVisibleOffHand(visibleOffHand)
                .build();
    }
}
