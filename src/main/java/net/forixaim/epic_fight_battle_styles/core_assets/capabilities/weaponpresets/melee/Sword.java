package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.StyleComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditional;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType.SKILL_EXISTENCE;

public class Sword
{
	public static StyleComboProvider styleComboProvider = new StyleComboProvider()
			.addConditional(new ProviderConditional(SKILL_EXISTENCE, ImperatriceLumiereStyles.IMPERATRICE_SWORD, SkillRegistry.IMPERATRICE_LUMIERE, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null))
			.addConditional(new ProviderConditional(ProviderConditionalType.COMPOSITE, HeroStyles.HERO_SWORD_SHIELD, null,
					new ProviderConditional(SKILL_EXISTENCE, null, SkillRegistry.HERO, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null),
					new ProviderConditional(ProviderConditionalType.WEAPON_CATEGORY, null, InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SHIELD, null, null)))
			.addConditional(new ProviderConditional(SKILL_EXISTENCE, HeroStyles.HERO_SWORD, SkillRegistry.HERO, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null));

	public static ComboProvider comboProvider = new ComboProvider()
			.addConditional(new ProviderConditional(SKILL_EXISTENCE, null, SkillRegistry.IMPERATRICE_LUMIERE, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, false));

	/**
	 * A typical attack cycle lambda function should have the following in an order deemed readable.
	 * 1. Variable Declarations from the Pair parameter
	 * 2. Living Motion Animations
	 * 3. Attack Cycle Animations
	 * 4. Innate Skill
	 * It should Always return Builder
	 */
	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> heroSwordAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, BattleAnimations.HERO_SWORD_IDLE);
		builder.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.BLOCK, BattleAnimations.HERO_SWORD_GUARD);
		builder.newStyleCombo(style,
				BattleAnimations.HERO_SWORD_AUTO_1,
				BattleAnimations.HERO_SWORD_AUTO_2,
				BattleAnimations.HERO_SWORD_AUTO_3,
				Animations.LONGSWORD_DASH,
				Animations.LONGSWORD_AIR_SLASH);
		builder.innateSkill(style, (itemStack -> SkillRegistry.SLAMMING_HERO));
		return builder;
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> heroSwordShieldAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, BattleAnimations.HERO_SWORD_IDLE);
		builder.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.BLOCK, BattleAnimations.HERO_SHIELD_BLOCK);
		builder.newStyleCombo(style,
				BattleAnimations.HERO_SWORD_AUTO_1,
				BattleAnimations.HERO_SHIELD_AUTO_1,
				BattleAnimations.HERO_SWORD_AUTO_2,
				BattleAnimations.HERO_SHIELD_AUTO_2,
				BattleAnimations.HERO_SWORD_AUTO_3,
				Animations.LONGSWORD_DASH,
				Animations.LONGSWORD_AIR_SLASH);
		builder.innateSkill(style, (itemStack -> SkillRegistry.SLAMMING_HERO));
		return builder;
	};


	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultOneHandAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.BLOCK, Animations.SWORD_GUARD);
		builder.newStyleCombo(style,
				Animations.SWORD_AUTO1,
				Animations.SWORD_AUTO2,
				Animations.SWORD_AUTO3,
				Animations.SWORD_DASH,
				Animations.SWORD_AIR_SLASH);
		builder.innateSkill(style, (itemStack -> EpicFightSkills.SWEEPING_EDGE));
		return builder;
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultTwoHandAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD);
		builder.livingMotionModifier(style, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.livingMotionModifier(style, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.livingMotionModifier(style, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_RUN_DUAL);
		builder.livingMotionModifier(style, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.livingMotionModifier(style, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.livingMotionModifier(style, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON);
		builder.newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH);
		builder.innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> EpicFightSkills.DANCING_EDGE);
		return builder;
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> imperatriceLumiere = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, BattleAnimations.IMPERATRICE_SWORD_EN_GARDE);
		builder.livingMotionModifier(style, LivingMotions.WALK, BattleAnimations.IMPERATRICE_SWORD_WALK);
		builder.livingMotionModifier(style, LivingMotions.RUN, BattleAnimations.IMPERATRICE_SWORD_RUN);
		builder.newStyleCombo(style,
				BattleAnimations.IMPERATRICE_SWORD_JAB1,
				BattleAnimations.IMPERATRICE_SWORD_JAB2,
				BattleAnimations.IMPERATRICE_SWORD_JAB3,
				BattleAnimations.IMPERATRICE_SWORD_JAB4,
				BattleAnimations.IMPERATRICE_SWORD_JAB5,
				BattleAnimations.IMPERATRICE_SWORD_DASH_ATTACK,
				BattleAnimations.IMPERATRICE_SWORD_AERIAL
		);
		builder.innateSkill(style, (itemstack) -> SkillRegistry.BLAZE_STINGER);
		return builder;
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> mountedAttack = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.newStyleCombo(style,
				Animations.SWORD_MOUNT_ATTACK
		);
		return builder;
	};



	private static final EFBSWeaponCapability.Builder swordBuilder = EFBSWeaponCapability.builder()
			.redirectedCategory(CapabilityItem.WeaponCategories.SWORD)
			.redirectedProvider(styleComboProvider
					.addDefaultConditional(new ProviderConditional(ProviderConditionalType.WEAPON_CATEGORY, CapabilityItem.Styles.TWO_HAND, InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SWORD, null, null))
					.addDefaultConditional(new ProviderConditional(ProviderConditionalType.DEFAULT, CapabilityItem.Styles.ONE_HAND, null))
					.exportStyle()
			)
			.redirectedCollider(ColliderPreset.SWORD)
			.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
			.redirectedSwingSound(EpicFightSounds.WHOOSH.get())
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Sword.defaultOneHandAttackCycle)
			.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Sword.defaultTwoHandAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD, Sword.heroSwordAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD_SHIELD, Sword.heroSwordShieldAttackCycle)
			.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
			.createStyleCategory(CapabilityItem.Styles.MOUNT, Sword.mountedAttack)
			.redirectedPredicator(comboProvider
					.addDefaultConditional(new ProviderConditional(ProviderConditionalType.WEAPON_CATEGORY, null, InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SWORD, null, true))
					.addDefaultConditional(new ProviderConditional(ProviderConditionalType.WEAPON_CATEGORY, null, InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SHIELD, null, true))
					.exportCombination()
			);

	public static CapabilityItem.Builder getBuilder()
	{
		return swordBuilder;
	}

	public static EFBSWeaponCapability.Builder modifyBuilder()
	{
		return swordBuilder;
	}
	public static StyleComboProvider getStyleProvider()
	{
		return styleComboProvider;
	}
	public static ComboProvider getComboProvider()
	{
		return comboProvider;
	}


}
