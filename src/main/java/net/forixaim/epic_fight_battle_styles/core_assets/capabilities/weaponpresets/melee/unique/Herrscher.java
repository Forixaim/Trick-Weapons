package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.unique;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.WoMUniqueStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.ModList;
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
	public Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
	{
		if (entityPatch instanceof PlayerPatch<?> playerPatch)
		{
			if ((playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getSkill() == SkillRegistry.ATLANTEAN && ModList.get().isLoaded("wom") && playerPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get())))
			{
				return WoMUniqueStyles.ATLANTEAN;
			}
			if (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getSkill() == SkillRegistry.IMPERATRICE_LUMIERE)
			{
				return ImperatriceLumiereStyles.IMPERATRICE_SWORD;
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

	public Function<LivingEntityPatch<?>, Boolean> comboPredicator = (main) ->
	{
		if (main instanceof PlayerPatch<?> playerPatch)
		{
			if (playerPatch.getSkill(SkillRegistry.IMPERATRICE_LUMIERE) != null)
			{
				return false;
			}
		}
		if (ModList.get().isLoaded("wom"))
			return EpicFightCapabilities.getItemStackCapability(main.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || main.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get());
		else
			return EpicFightCapabilities.getItemStackCapability(main.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD;
	};

	public  Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> atlanteanAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		if (ModList.get().isLoaded("wom"))
		{
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
		}

		return builder;
	};
}
