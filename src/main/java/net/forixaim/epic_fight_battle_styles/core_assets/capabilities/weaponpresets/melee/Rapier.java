package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLuminelleStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.offHandItem;
import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.skillCheck;

public class Rapier
{
	public static Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
	{
		if (skillCheck(entityPatch, SkillRegistry.IMPERATRICE_LUMINELLE))
		{
			return ImperatriceLuminelleStyles.SWORD;
		}
		else if (offHandItem(entityPatch, CapabilityItem.WeaponCategories.SWORD))
		{
			return CapabilityItem.Styles.TWO_HAND;
		}
		else
		{
			return CapabilityItem.Styles.ONE_HAND;
		}
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultOneHandAttack = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, BattleAnimations.RAPIER_IDLE);
		builder.livingMotionModifier(style, LivingMotions.WALK, BattleAnimations.RAPIER_WALK);
		builder.newStyleCombo(style,
				BattleAnimations.RAPIER_AUTO1,
				BattleAnimations.RAPIER_AUTO2,
				Animations.GRASPING_SPIRAL_SECOND,
				Animations.LONGSWORD_DASH,
				Animations.SPEAR_ONEHAND_AIR_SLASH);
		return builder;
	};
}
