package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.recruit;

import net.forixaim.battle_arts.core_assets.animations.battle_style.novice.recruit.RecruitSpearAnimations;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.Recruit;
import net.forixaim.efm_ex.api.moveset.MoveSet;
import yesman.epicfight.api.animation.LivingMotions;

public class RecruitMoveSets
{
    public static final MoveSet RECRUIT_MOVESET = MoveSet.builder()
            .addLivingMotionModifier(LivingMotions.IDLE, RecruitSpearAnimations.RECRUIT_SPEAR_IDLE)
            .addLivingMotionModifier(LivingMotions.WALK, RecruitSpearAnimations.RECRUIT_SPEAR_WALK)
            .addLivingMotionModifier(LivingMotions.RUN, RecruitSpearAnimations.RECRUIT_SPEAR_RUN)
            .addLivingMotionModifier(LivingMotions.KNEEL, RecruitSpearAnimations.RECRUIT_SPEAR_CROUCH)
            .addAutoAttacks(
                    RecruitSpearAnimations.RECRUIT_SPEAR_STANDING_ATTACK, RecruitSpearAnimations.RECRUIT_SPEAR_STANDING_ATTACK_2,
                    RecruitSpearAnimations.RECRUIT_SPEAR_DASH_ATTACK, RecruitSpearAnimations.RECRUIT_SPEAR_AERIAL_POKE
            )
            .addInnateSkill(itemStack -> Recruit.IRON_FORTRESS)
            .build();
}
