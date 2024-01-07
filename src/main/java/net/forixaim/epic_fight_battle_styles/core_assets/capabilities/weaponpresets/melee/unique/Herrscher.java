package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.unique;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLuminelleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.WoMUniqueStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.world.InteractionHand;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

public class Herrscher
{
	public static Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
	{
		if (entityPatch instanceof PlayerPatch<?> playerPatch)
		{
			if (playerPatch.getSkill(SkillRegistry.ATLANTEAN) != null && playerPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get()))
			{
				return WoMUniqueStyles.ATLANTEAN;
			}
			if (playerPatch.getSkill(SkillRegistry.IMPERATRICE_LUMINELLE) != null)
			{
				return ImperatriceLuminelleStyles.SWORD;
			}
		}
		if (entityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD && !entityPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get()))
		{
			return CapabilityItem.Styles.TWO_HAND;
		}
		else
		{
			return CapabilityItem.Styles.ONE_HAND;
		}
	};

	public static Function<LivingEntityPatch<?>, Boolean> comboPredicator = (main) ->
	{
		if (main instanceof PlayerPatch<?> playerPatch)
		{
			if (playerPatch.getSkill(SkillRegistry.IMPERATRICE_LUMINELLE) != null)
			{
				return false;
			}
		}
		return EpicFightCapabilities.getItemStackCapability(main.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || main.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get());
	};

	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> atlanteanAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, WOMAnimations.HERRSCHER_IDLE)
				.livingMotionModifier(style, LivingMotions.WALK, WOMAnimations.HERRSCHER_WALK)
				.livingMotionModifier(style, LivingMotions.RUN, WOMAnimations.HERRSCHER_RUN)
				.livingMotionModifier(style, LivingMotions.BLOCK, WOMAnimations.HERRSCHER_GUARD);
		builder.newStyleCombo(style,
				WOMAnimations.HERRSCHER_AUTO_1,
				WOMAnimations.HERRSCHER_AUTO_2,
				WOMAnimations.HERRSCHER_AUTO_3,
				WOMAnimations.HERRSCHER_BEFREIUNG,
				WOMAnimations.HERRSCHER_AUSROTTUNG
		);
		builder.innateSkill(style, (itemstack) -> WOMSkills.REGIERUNG);
		return builder;
	};
}
