package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import com.mojang.datafixers.util.Pair;
import net.forixaim.battle_arts.core_assets.animations.battle_style.novice.recruit.RecruitSpearAnimations;
import net.forixaim.battle_arts.core_assets.animations.battle_style.novice.squire.SquireSwordAnimations;
import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.RecruitWieldStyles;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.NoviceBattleStyles;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.Squire;
import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillSlots;
import net.forixaim.efm_ex.api.providers.ProviderConditional;
import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.SpearType;
import net.forixaim.efm_ex.capabilities.weaponcaps.EXWeaponCapability;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

public class SpearAttacks
{
	public static final ProviderConditional RECRUIT_SPEAR_CHECK = CoreCapability.COMBO_PROVIDER_REGISTRY.add(
			"recruit_spear_combo",
			ProviderConditionalType.SKILL_EXISTENCE,
			BattleArtsSkillSlots.BATTLE_STYLE,
			NoviceBattleStyles.RECRUIT,
			RecruitWieldStyles.SPEAR,
			false,
			null
	);

	public static void loadAttacks()
	{
		SpearType.getInstance().getStyleComboProviderRegistry().add(RECRUIT_SPEAR_CHECK);
		SpearType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(
				RecruitWieldStyles.SPEAR, recruitSpearCombo
		));
	}

	public static final Function<Pair<Style, EXWeaponCapability.Builder>, EXWeaponCapability.Builder> recruitSpearCombo = (pair) ->
	{
		EXWeaponCapability.Builder builder = pair.getSecond();

		builder.livingMotionModifier(pair.getFirst(), LivingMotions.IDLE, RecruitSpearAnimations.RECRUIT_SPEAR_IDLE)
				.livingMotionModifier(pair.getFirst(), LivingMotions.WALK, RecruitSpearAnimations.RECRUIT_SPEAR_WALK)
				.livingMotionModifier(pair.getFirst(), LivingMotions.RUN, RecruitSpearAnimations.RECRUIT_SPEAR_RUN);
		return pair.getSecond();
	};
}
