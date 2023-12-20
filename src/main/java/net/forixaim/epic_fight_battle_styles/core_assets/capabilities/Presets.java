package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.DuelistStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLuminelleStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

//This is where all the weapon capability presets are implemented
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Presets
{
	public static final Function<Item, CapabilityItem.Builder> RAPIER = (item) ->
			WeaponCapability.builder()
					.styleProvider(
							(playerpatch) ->
							{
								if (((PlayerPatch) playerpatch).getSkill(SkillRegistry.DUELIST) != null)
								{
									return DuelistStyles.DUELIST_RAPIER;
								}
								else
								{
									return CapabilityItem.Styles.ONE_HAND;
								}
							}
					)
					.canBePlacedOffhand(false)
					.collider(ColliderPreset.LONGSWORD)
					.category(CapabilityItem.WeaponCategories.SWORD)
					.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, BattleAnimations.RAPIER_IDLE);
	public static final Function<Item, CapabilityItem.Builder> CHAKRAM = (item) ->
			WeaponCapability.builder()
					.styleProvider(
							(playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == BattleStyleCategories.CHAKRAM ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
					.category(BattleStyleCategories.CHAKRAM)
					.hitSound(EpicFightSounds.BLADE_HIT.get())
					.innateSkill(CapabilityItem.Styles.ONE_HAND, (itemStack) -> SkillRegistry.PRECISION_VERTICAL)
					.swingSound(EpicFightSounds.WHOOSH_SMALL.get())
					.newStyleCombo(CapabilityItem.Styles.ONE_HAND,
							BattleAnimations.SINGLE_CHAKRAM_AUTO_1,
							BattleAnimations.SINGLE_CHAKRAM_AUTO_2,
							BattleAnimations.SINGLE_CHAKRAM_DASH_ATTACK, BattleAnimations.SINGLE_CHAKRAM_AIR_SLASH)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, BattleAnimations.SINGLE_CHAKRAM_IDLE)
					.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, BattleAnimations.SINGLE_CHAKRAM_WALK)
				.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == BattleStyleCategories.CHAKRAM);
	public static final Function<Item, CapabilityItem.Builder> SWORD = (item) ->
			WeaponCapability.builder()
				.category(CapabilityItem.WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> {
					if (playerpatch.getOriginal().getType() != EntityType.PLAYER)
					{
						if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD) {
							return CapabilityItem.Styles.TWO_HAND;
						}
						else
						{
							return CapabilityItem.Styles.ONE_HAND;
						}
					}
					if (((PlayerPatch) playerpatch).getSkill(SkillRegistry.HERO) != null)
					{
						if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD)
						{
							return HeroStyles.HERO_SWORD_SHIELD;
						}
						return HeroStyles.HERO_SWORD;
					}
					if (((PlayerPatch) playerpatch).getSkill(SkillRegistry.DUELIST) != null)
					{
						if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD)
						{
							return DuelistStyles.DUELIST_DUAL_SWORD;
						}
						else
						{
							return DuelistStyles.DUELIST_SWORD;
						}
					}
					else if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD) {
						return CapabilityItem.Styles.TWO_HAND;
					}
					else
					{
						return CapabilityItem.Styles.ONE_HAND;
					}
				})
				.collider(ColliderPreset.SWORD)
				.newStyleCombo(CapabilityItem.Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
					.newStyleCombo(HeroStyles.HERO_SWORD, BattleAnimations.HERO_SWORD_AUTO_1, BattleAnimations.HERO_SWORD_AUTO_2, BattleAnimations.HERO_SWORD_AUTO_3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> EpicFightSkills.SWEEPING_EDGE)
					.newStyleCombo(HeroStyles.HERO_SWORD_SHIELD, BattleAnimations.HERO_SWORD_AUTO_1, BattleAnimations.HERO_SHIELD_AUTO_1, BattleAnimations.HERO_SWORD_AUTO_2, BattleAnimations.HERO_SHIELD_AUTO_2, BattleAnimations.HERO_SWORD_AUTO_3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> EpicFightSkills.DANCING_EDGE)
					.innateSkill(HeroStyles.HERO_SWORD, (itemStack -> SkillRegistry.SLAMMING_HERO))
					.innateSkill(HeroStyles.HERO_SWORD_SHIELD, (itemStack -> SkillRegistry.SLAMMING_HERO))
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON)
				.livingMotionModifier(HeroStyles.HERO_SWORD, LivingMotions.IDLE, BattleAnimations.HERO_SWORD_IDLE)
					.livingMotionModifier(HeroStyles.HERO_SWORD, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
					.livingMotionModifier(HeroStyles.HERO_SWORD, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
					.livingMotionModifier(HeroStyles.HERO_SWORD, LivingMotions.BLOCK, BattleAnimations.HERO_SWORD_GUARD)
					.livingMotionModifier(HeroStyles.HERO_SWORD_SHIELD, LivingMotions.IDLE, BattleAnimations.HERO_SWORD_IDLE)
					.livingMotionModifier(HeroStyles.HERO_SWORD_SHIELD, LivingMotions.BLOCK, BattleAnimations.HERO_SHIELD_BLOCK)
					.weaponCombinationPredicator((entitypatch) ->
					{
						//Safety Check
						if (entitypatch.getOriginal().getType() != EntityType.PLAYER)
						{
							if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD)
							{
								return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD;
							}
							else
							{
								return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD;
							}
						}
						//Skills Check

						//Default Check
						if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD)
						{
							return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD;
						}
						else
						{
							return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD;
						}
					});

	public static final Function<Item, CapabilityItem.Builder> LONGSWORD = (item) ->
			WeaponCapability.builder()
					.category(CapabilityItem.WeaponCategories.LONGSWORD)
					.styleProvider((playerpatch) ->
					{
						// A little check if the 'playerpatch' is a player since there are casting errors if the entity is not a player
						if (playerpatch.getOriginal().getType() != EntityType.PLAYER)
						{
							return CapabilityItem.Styles.TWO_HAND;
						}
						if (((PlayerPatch) playerpatch).getSkill(SkillRegistry.IMPERATRICE_LUMINELLE) != null)
						{
							return ImperatriceLuminelleStyles.SWORD;
						}
						if (((PlayerPatch) playerpatch).getSkill(SkillRegistry.HERO) != null)
						{
							if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD)
							{
								return HeroStyles.HERO_SWORD_SHIELD;
							}
							return HeroStyles.HERO_SWORD;
						}
						else if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD)
						{
							return CapabilityItem.Styles.ONE_HAND;
						}
						else if (playerpatch instanceof PlayerPatch<?> tplayerpatch)
						{
							return tplayerpatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? CapabilityItem.Styles.OCHS : CapabilityItem.Styles.TWO_HAND;
						}

						return CapabilityItem.Styles.TWO_HAND;
					})
					.hitSound(EpicFightSounds.BLADE_HIT.get())
					.collider(ColliderPreset.LONGSWORD)
					.canBePlacedOffhand(false)
					.newStyleCombo(ImperatriceLuminelleStyles.SWORD, BattleAnimations.IMPERATRICE_SWORD_AUTO1, BattleAnimations.IMPERATRICE_SWORD_AUTO2, BattleAnimations.IMPERATRICE_SWORD_FLAME_DANCE, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.newStyleCombo(HeroStyles.HERO_SWORD, BattleAnimations.HERO_SWORD_AUTO_1, BattleAnimations.HERO_SWORD_AUTO_2, BattleAnimations.HERO_SWORD_AUTO_3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.newStyleCombo(HeroStyles.HERO_SWORD_SHIELD, BattleAnimations.HERO_SWORD_AUTO_1, BattleAnimations.HERO_SHIELD_AUTO_1, BattleAnimations.HERO_SWORD_AUTO_2, BattleAnimations.HERO_SHIELD_AUTO_2, BattleAnimations.HERO_SWORD_AUTO_3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.innateSkill(HeroStyles.HERO_SWORD, (itemStack -> SkillRegistry.SLAMMING_HERO))
					.innateSkill(HeroStyles.HERO_SWORD_SHIELD, (itemStack -> SkillRegistry.SLAMMING_HERO))
					.newStyleCombo(CapabilityItem.Styles.ONE_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.newStyleCombo(CapabilityItem.Styles.OCHS, Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.LONGSWORD_LIECHTENAUER_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> EpicFightSkills.SHARP_STAB)
					.innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> EpicFightSkills.LIECHTENAUER)
					.innateSkill(CapabilityItem.Styles.OCHS, (itemstack) -> EpicFightSkills.LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.RUN, Animations.BIPED_HOLD_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER)
					.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
					.livingMotionModifier(HeroStyles.HERO_SWORD, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.OCHS, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
					.livingMotionModifier(HeroStyles.HERO_SWORD, LivingMotions.IDLE, BattleAnimations.HERO_SWORD_IDLE)
					.livingMotionModifier(HeroStyles.HERO_SWORD, LivingMotions.BLOCK, BattleAnimations.HERO_SWORD_GUARD)
					.livingMotionModifier(HeroStyles.HERO_SWORD_SHIELD, LivingMotions.IDLE, BattleAnimations.HERO_SWORD_IDLE)
					.livingMotionModifier(HeroStyles.HERO_SWORD_SHIELD, LivingMotions.BLOCK, BattleAnimations.HERO_SHIELD_BLOCK)
					.livingMotionModifier(HeroStyles.HERO_SWORD_SHIELD, LivingMotions.BLOCK_SHIELD, BattleAnimations.HERO_SHIELD_BLOCK)
					.livingMotionModifier(ImperatriceLuminelleStyles.SWORD, LivingMotions.IDLE, BattleAnimations.IMPERATRICE_SWORD_EN_GARDE)
					.livingMotionModifier(ImperatriceLuminelleStyles.SWORD, LivingMotions.WALK, BattleAnimations.IMPERATRICE_SWORD_WALK)
					.livingMotionModifier(ImperatriceLuminelleStyles.SWORD, LivingMotions.RUN, BattleAnimations.IMPERATRICE_SWORD_RUN)
					.weaponCombinationPredicator((entitypatch) ->
					{
						//Safety Check
						if (entitypatch.getOriginal().getType() != EntityType.PLAYER)
						{
							if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD)
							{
								return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD;
							}
							else
							{
								return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD;
							}
						}
						//Skills Check
						if (((PlayerPatch<?>) entitypatch).getSkill(SkillRegistry.IMPERATRICE_LUMINELLE) != null)
						{
							return false;
						}
						//Default Check
						if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD)
						{
							return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD;
						}
						else
						{
							return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD;
						}
					});;
	@SubscribeEvent
	public static void Register(WeaponCapabilityPresetRegistryEvent Event)
	{
		Event.getTypeEntry().put("epic_fight_battle_styles:chakram", CHAKRAM);
		Event.getTypeEntry().put("epic_fight_battle_styles:sword", SWORD);
		Event.getTypeEntry().put("epic_fight_battle_styles:longsword", LONGSWORD);
		Event.getTypeEntry().put("epic_fight_battle_styles:rapier", RAPIER);
	}
}
