package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.offHandItem;
import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.skillCheck;

public class Longsword
{
	private static final Logger LOGGER = LogUtils.getLogger();
	public static Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
	{
		if (skillCheck(entityPatch, SkillRegistry.IMPERATRICE_LUMIERE))
		{
			LOGGER.debug("Imperatrice Sword Style");
			return ImperatriceLumiereStyles.IMPERATRICE_SWORD;
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
		else if (offHandItem(entityPatch, CapabilityItem.WeaponCategories.SHIELD))
		{
			LOGGER.debug("Default One Hand Style");
			return CapabilityItem.Styles.ONE_HAND;
		}
		else if (entityPatch instanceof PlayerPatch<?> tplayerpatch)
		{

			return tplayerpatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? CapabilityItem.Styles.OCHS : CapabilityItem.Styles.TWO_HAND;
		}
		LOGGER.debug("Default Two Hand Style");
		return CapabilityItem.Styles.TWO_HAND;
	};

	public static Function<LivingEntityPatch<?>, Boolean> comboPredicator = (entityPatch) ->
	{
		//Skills Check
		if (skillCheck(entityPatch, SkillRegistry.IMPERATRICE_LUMIERE))
		{
			return false;
		}
		//Default Check
		return EpicFightCapabilities.getItemStackCapability(entityPatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD;
	};

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
}
