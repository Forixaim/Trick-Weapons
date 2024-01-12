package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLuminelleStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.offHandItem;
import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.skillCheck;

public class Sword
{
	private static final Logger LOGGER = LogUtils.getLogger();
	/**
	 * A logic function that determines what style the weapon is.
	 */
	public static Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
	{
		if (skillCheck(entityPatch, SkillRegistry.IMPERATRICE_LUMINELLE))
		{
			LOGGER.debug("Imperatrice Sword Style");
			return ImperatriceLuminelleStyles.SWORD;
		}
		if (skillCheck(entityPatch, SkillRegistry.HERO))
		{
			if (offHandItem(entityPatch, CapabilityItem.WeaponCategories.SHIELD))
			{
				LOGGER.debug("Hero Sword Shield Style");
				return HeroStyles.HERO_SWORD_SHIELD;
			}
			LOGGER.debug("Hero Sword Style");
			return HeroStyles.HERO_SWORD;
		}
		else if (offHandItem(entityPatch, CapabilityItem.WeaponCategories.SWORD))
		{
			LOGGER.debug("Default One Hand Style");
			return CapabilityItem.Styles.TWO_HAND;
		}
		else
		{
			LOGGER.debug("Default Two Hand Style");
			return CapabilityItem.Styles.ONE_HAND;
		}
	};

	public static Function<LivingEntityPatch<?>, Boolean> comboPredicator = (entityPatch) ->
	{
		if (skillCheck(entityPatch, SkillRegistry.IMPERATRICE_LUMINELLE))
			return false;
		//Default Check
		if (offHandItem(entityPatch, CapabilityItem.WeaponCategories.SWORD))
		{
			return true;
		}
		else
		{
			return EpicFightCapabilities.getItemStackCapability(entityPatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD;
		}
	};

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

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> imperatriceLuminelle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, BattleAnimations.IMPERATRICE_SWORD_EN_GARDE);
		builder.livingMotionModifier(style, LivingMotions.WALK, BattleAnimations.IMPERATRICE_SWORD_WALK);
		builder.livingMotionModifier(style, LivingMotions.RUN, BattleAnimations.IMPERATRICE_SWORD_RUN);
		builder.newStyleCombo(style,
				BattleAnimations.IMPERATRICE_SWORD_AUTO1,
				BattleAnimations.IMPERATRICE_SWORD_AUTO2,
				BattleAnimations.IMPERATRICE_SWORD_FLAME_DANCE,
				BattleAnimations.IMPERATRICE_SWORD_INFERNAL_WHEEL,
				Animations.LONGSWORD_AIR_SLASH);
		return builder;
	};
}
