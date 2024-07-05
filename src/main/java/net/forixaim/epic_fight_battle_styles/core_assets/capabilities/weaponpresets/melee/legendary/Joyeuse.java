package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.legendary;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditional;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.StyleComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Longsword;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Sword;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType.*;
import static net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType.DEFAULT;

public class Joyeuse
{
	public static StyleComboProvider styleComboProvider = new StyleComboProvider()
			.addConditional(new ProviderConditional(SKILL_EXISTENCE, ImperatriceLumiereStyles.IMPERATRICE_SWORD, SkillRegistry.IMPERATRICE_LUMIERE, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null))
			.addConditional(new ProviderConditional(ProviderConditionalType.COMPOSITE, HeroStyles.HERO_SWORD_SHIELD, null,
					new ProviderConditional(SKILL_EXISTENCE, null, SkillRegistry.HERO, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null),
					new ProviderConditional(ProviderConditionalType.WEAPON_CATEGORY, null, InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SHIELD, null, null)))
			.addConditional(new ProviderConditional(SKILL_EXISTENCE, HeroStyles.HERO_SWORD, SkillRegistry.HERO, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null));

	public static ComboProvider comboProvider = new ComboProvider()
			.addConditional(new ProviderConditional(SKILL_EXISTENCE, null, SkillRegistry.IMPERATRICE_LUMIERE, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, false));

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultTwoHandAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
		builder.newStyleCombo(style,
				Animations.LONGSWORD_AUTO1,
				Animations.LONGSWORD_AUTO2,
				Animations.LONGSWORD_AUTO3,
				Animations.LONGSWORD_DASH,
				Animations.LONGSWORD_AIR_SLASH
		);
		builder.innateSkill(style, (itemstack) -> EpicFightSkills.LIECHTENAUER);
		return builder;
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultOneHandAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD);
		builder.livingMotionModifier(style, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
		builder.newStyleCombo(style,
				Animations.LONGSWORD_AUTO1,
				Animations.LONGSWORD_AUTO2,
				Animations.LONGSWORD_AUTO3,
				Animations.LONGSWORD_DASH,
				Animations.LONGSWORD_AIR_SLASH
		);
		builder.innateSkill(style, (itemstack) -> EpicFightSkills.SHARP_STAB);
		return builder;
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> LiechtenauerAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_HOLD_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER);
		builder.livingMotionModifier(style, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
		builder.newStyleCombo(style,
				Animations.LONGSWORD_LIECHTENAUER_AUTO1,
				Animations.LONGSWORD_LIECHTENAUER_AUTO2,
				Animations.LONGSWORD_LIECHTENAUER_AUTO3,
				Animations.LONGSWORD_DASH,
				Animations.LONGSWORD_AIR_SLASH
		);
		builder.innateSkill(style, (itemstack) -> EpicFightSkills.SHARP_STAB);
		return builder;
	};

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

	private static final EFBSWeaponCapability.Builder joyeuseBuilder = EFBSWeaponCapability.builder()
			.redirectedCategory(CapabilityItem.WeaponCategories.LONGSWORD)
			.redirectedCollider(ColliderPreset.LONGSWORD)
			.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
			.redirectedPassiveSkill(SkillRegistry.JOYEUSE_PASSIVE)
			.redirectedProvider(Longsword.styleComboProvider
					.addDefaultConditional(new ProviderConditional(WEAPON_CATEGORY, CapabilityItem.Styles.ONE_HAND, InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SHIELD, null, null))
					.addDefaultConditional(new ProviderConditional(SKILL_ACTIVATION, CapabilityItem.Styles.OCHS, EpicFightSkills.LIECHTENAUER, SkillSlots.WEAPON_INNATE, null, null))
					.addDefaultConditional(new ProviderConditional(DEFAULT, CapabilityItem.Styles.TWO_HAND, null))
					.exportStyle())
			.redirectedSwingSound(EpicFightSounds.WHOOSH.get())
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Longsword.defaultOneHandAttackCycle)
			.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Longsword.defaultTwoHandAttackCycle)
			.createStyleCategory(CapabilityItem.Styles.OCHS, Longsword.LiechtenauerAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD, Longsword.heroSwordAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD_SHIELD, Longsword.heroSwordShieldAttackCycle)
			.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
			.createStyleCategory(CapabilityItem.Styles.MOUNT, Sword.mountedAttack)
			.redirectedPredicator(Longsword.comboProvider
					.addDefaultConditional(new ProviderConditional(WEAPON_CATEGORY, null, InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SHIELD, null, true))
					.addDefaultConditional(new ProviderConditional(DEFAULT, null, false)).exportCombination())
			.offHandUse(false);

	public static CapabilityItem.Builder getBuilder()
	{
		return joyeuseBuilder;
	}

	public static EFBSWeaponCapability.Builder modifyBuilder()
	{
		return joyeuseBuilder;
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
