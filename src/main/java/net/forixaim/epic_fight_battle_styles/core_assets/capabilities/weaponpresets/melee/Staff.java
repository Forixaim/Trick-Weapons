//package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;
//
//import com.mojang.datafixers.util.Pair;
//import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
//import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
//import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
//import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLuminelleStyles;
//import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
//import reascer.wom.gameasset.WOMAnimations;
//import reascer.wom.gameasset.WOMSkills;
//import yesman.epicfight.api.animation.LivingMotions;
//import yesman.epicfight.gameasset.Animations;
//import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
//import yesman.epicfight.world.capabilities.item.CapabilityItem;
//import yesman.epicfight.world.capabilities.item.Style;
//
//import java.util.function.Function;
//
//import static net.forixaim.epic_fight_battle_styles.core_assets.api.providers.HelperFunctions.offHandItem;
//import static net.forixaim.epic_fight_battle_styles.core_assets.api.providers.HelperFunctions.skillCheck;
//
//public class Staff
//{
//	public static Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
//	{
//		return CapabilityItem.Styles.TWO_HAND;
//	};
//	public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultAttackCycle = (main) ->
//	{
//		EFBSWeaponCapability.Builder builder = main.getSecond();
//		Style style = main.getFirst();
//		builder.livingMotionModifier(style, LivingMotions.IDLE, WOMAnimations.STAFF_IDLE);
//		builder.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR);
//		builder.livingMotionModifier(style, LivingMotions.CHASE, WOMAnimations.STAFF_RUN);
//		builder.livingMotionModifier(style, LivingMotions.RUN, WOMAnimations.STAFF_RUN);
//		builder.livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR);
//		builder.livingMotionModifier(style, LivingMotions.BLOCK, Animations.SPEAR_GUARD);
//		builder.newStyleCombo(style,
//				WOMAnimations.STAFF_AUTO_1,
//				WOMAnimations.STAFF_AUTO_2,
//				WOMAnimations.STAFF_AUTO_3,
//				WOMAnimations.STAFF_DASH,
//				WOMAnimations.STAFF_KINKONG
//		);
//		builder.innateSkill(style, (itemStack) -> WOMSkills.CHARYBDIS);
//		return builder;
//	};
//}
