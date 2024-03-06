package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.unique;

//public class Herrscher
//{
//	public Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
//	{
//		if (entityPatch instanceof PlayerPatch<?> playerPatch)
//		{
//			if (playerPatch.getSkill(SkillRegistry.ATLANTEAN) != null && ModList.get().isLoaded("wom") && playerPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get()))
//			{
//				return WoMUniqueStyles.ATLANTEAN;
//			}
//			if (playerPatch.getSkill(SkillRegistry.IMPERATRICE_LUMINELLE) != null)
//			{
//				return ImperatriceLuminelleStyles.SWORD;
//			}
//		}
//		if (entityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD && !entityPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get()))
//		{
//			return CapabilityItem.Styles.TWO_HAND;
//		}
//		else
//		{
//			return CapabilityItem.Styles.ONE_HAND;
//		}
//	};
//
//	public Function<LivingEntityPatch<?>, Boolean> comboPredicator = (main) ->
//	{
//		if (main instanceof PlayerPatch<?> playerPatch)
//		{
//			if (playerPatch.getSkill(SkillRegistry.IMPERATRICE_LUMINELLE) != null)
//			{
//				return false;
//			}
//		}
//		if (ModList.get().isLoaded("wom"))
//			return EpicFightCapabilities.getItemStackCapability(main.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || main.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get());
//		else
//			return EpicFightCapabilities.getItemStackCapability(main.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD;
//	};
//
//	public  Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> atlanteanAttackCycle = (main) ->
//	{
//		EFBSWeaponCapability.Builder builder = main.getSecond();
//		Style style = main.getFirst();
//		if (ModList.get().isLoaded("wom"))
//		{
//			builder.livingMotionModifier(style, LivingMotions.IDLE, WOMAnimations.HERRSCHER_IDLE)
//					.livingMotionModifier(style, LivingMotions.WALK, WOMAnimations.HERRSCHER_WALK)
//					.livingMotionModifier(style, LivingMotions.RUN, WOMAnimations.HERRSCHER_RUN)
//					.livingMotionModifier(style, LivingMotions.BLOCK, WOMAnimations.HERRSCHER_GUARD);
//			builder.newStyleCombo(style,
//					WOMAnimations.HERRSCHER_AUTO_1,
//					WOMAnimations.HERRSCHER_AUTO_2,
//					WOMAnimations.HERRSCHER_AUTO_3,
//					WOMAnimations.HERRSCHER_BEFREIUNG,
//					WOMAnimations.HERRSCHER_AUSROTTUNG
//			);
//			builder.innateSkill(style, (itemstack) -> WOMSkills.REGIERUNG);
//		}
//
//		return builder;
//	};
//}
