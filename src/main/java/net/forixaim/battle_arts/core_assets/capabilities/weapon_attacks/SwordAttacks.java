package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import com.mojang.datafixers.util.Pair;
import net.forixaim.battle_arts.core_assets.animations.battle_style.novice.squire.SquireSwordAnimations;
import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.SquireWieldStyles;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.NoviceBattleStyles;
import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillSlots;
import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.SwordType;
import net.forixaim.efm_ex.capabilities.weaponcaps.EXWeaponCapability;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

public class SwordAttacks
{
	public static void loadAttacks()
	{
		SwordType.getInstance().getStyleComboProviderRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(
				"squire_sword_combo",
				ProviderConditionalType.SKILL_EXISTENCE,
				BattleArtsSkillSlots.BATTLE_STYLE,
				NoviceBattleStyles.SQUIRE,
				SquireWieldStyles.SQUIRE_SWORD,
				false,
				null
		));

		SwordType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(
				SquireWieldStyles.SQUIRE_SWORD, squireSwordCombo
		));
	}

	public static final Function<Pair<Style, EXWeaponCapability.Builder>, EXWeaponCapability.Builder> squireSwordCombo = (pair) ->
	{
		pair.getSecond().livingMotionModifier(pair.getFirst(), LivingMotions.IDLE, SquireSwordAnimations.SQUIRE_SWORD_IDLE);
		pair.getSecond().livingMotionModifier(pair.getFirst(), LivingMotions.WALK, SquireSwordAnimations.SQUIRE_SWORD_WALK);
		pair.getSecond().livingMotionModifier(pair.getFirst(), LivingMotions.RUN, SquireSwordAnimations.SQUIRE_SWORD_RUN);
		pair.getSecond().livingMotionModifier(pair.getFirst(), LivingMotions.KNEEL, SquireSwordAnimations.SQUIRE_SWORD_CROUCH);
		pair.getSecond().livingMotionModifier(pair.getFirst(), LivingMotions.SNEAK, SquireSwordAnimations.SQUIRE_SWORD_CROUCH_WALK);
		pair.getSecond().newStyleCombo(pair.getFirst(),
				SquireSwordAnimations.SQUIRE_SWORD_AUTO_1,
				SquireSwordAnimations.SQUIRE_SWORD_AUTO_2,
				SquireSwordAnimations.SQUIRE_SWORD_AUTO_3,
				Animations.SWORD_DASH,
				Animations.SWORD_AIR_SLASH
		);
		return pair.getSecond();
	};

}
