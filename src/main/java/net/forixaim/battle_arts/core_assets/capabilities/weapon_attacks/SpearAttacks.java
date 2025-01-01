package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import com.mojang.datafixers.util.Pair;
import net.forixaim.battle_arts.core_assets.animations.battle_style.novice.recruit.RecruitSpearAnimations;
import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.RecruitWieldStyles;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.recruit.RecruitMoveSets;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.NoviceBattleStyles;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.Recruit;
import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillSlots;
import net.forixaim.efm_ex.api.providers.ProviderConditional;
import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.SpearType;
import net.forixaim.efm_ex.capabilities.weaponcaps.EXWeaponCapability;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.novice.RecruitProviders.RECRUIT_SPEAR_CHECK;

public class SpearAttacks
{


	public static void loadAttacks()
	{
		SpearType.getInstance().getStyleComboProviderRegistry().add(RECRUIT_SPEAR_CHECK);
		SpearType.getInstance().getAttackSets().put(RecruitWieldStyles.RECRUIT_SPEAR, RecruitMoveSets.RECRUIT_MOVESET);
	}

	public static final Function<Pair<Style, EXWeaponCapability.Builder>, EXWeaponCapability.Builder> recruitSpearCombo = (pair) ->
	{
		EXWeaponCapability.Builder builder = pair.getSecond();

		builder.livingMotionModifier(pair.getFirst(), LivingMotions.IDLE, RecruitSpearAnimations.RECRUIT_SPEAR_IDLE)
				.livingMotionModifier(pair.getFirst(), LivingMotions.WALK, RecruitSpearAnimations.RECRUIT_SPEAR_WALK)
				.livingMotionModifier(pair.getFirst(), LivingMotions.RUN, RecruitSpearAnimations.RECRUIT_SPEAR_RUN)
				.livingMotionModifier(pair.getFirst(), LivingMotions.KNEEL, RecruitSpearAnimations.RECRUIT_SPEAR_CROUCH)
				.newStyleCombo(pair.getFirst(), RecruitSpearAnimations.RECRUIT_SPEAR_STANDING_ATTACK, RecruitSpearAnimations.RECRUIT_SPEAR_STANDING_ATTACK_2, RecruitSpearAnimations.RECRUIT_SPEAR_DASH_ATTACK, RecruitSpearAnimations.RECRUIT_SPEAR_AERIAL_POKE)
				.innateSkill(pair.getFirst(), (itemStack) -> Recruit.IRON_FORTRESS);
		return pair.getSecond();
	};
}
