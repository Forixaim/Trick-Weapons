package net.forixaim.trick_weapons.bridgeassets;

import com.google.common.collect.Maps;
import com.mojang.datafixers.types.Func;
import com.mojang.logging.LogUtils;
import net.forixaim.trick_weapons.TrickWeapons;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModLoader;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Map;
import java.util.function.Function;

/**
 * @brief This class is used to register the core weapon capabilities
 */
public class TrickWeaponsCapabilities {
	/**
	 * Placeholder animations
	 * Rapiers are light and thin swords that are used for consecutive light quick thrust attacks with some minor slashes.
	 */

	/**
	 * Chakram Animations with inspirations from Better Combat and Simply Swords
	 * Chakrams are small and lightweight throwing weapons that excel in ranged combat but can miss on close range encounters.
	 */
	public static final Function<Item, CapabilityItem.Builder> CHAKRAM = (item) -> {
		return WeaponCapability.builder()
				.styleProvider((playerpatch) ->
						playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == TrickWeaponCategories.CHAKRAM ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
				.category(TrickWeaponCategories.CHAKRAM)
				.hitSound(EpicFightSounds.BLADE_HIT.get())
				.swingSound(EpicFightSounds.WHOOSH_SMALL.get())
				.innateSkill(CapabilityItem.Styles.ONE_HAND, itemStack -> TrickWeaponsInnateSkills.PRECISION_VERTICAL)
				.innateSkill(CapabilityItem.Styles.TWO_HAND, itemStack -> TrickWeaponsInnateSkills.SPINNING_WHIRLWIND)
				.newStyleCombo(CapabilityItem.Styles.ONE_HAND,
						TrickWeaponsAnimations.CHAKRAM_AUTO1,
						TrickWeaponsAnimations.CHAKRAM_AUTO2,
						TrickWeaponsAnimations.CHAKRAM_DASH, TrickWeaponsAnimations.CHAKRAM_AIRSLASH)
				.newStyleCombo(CapabilityItem.Styles.TWO_HAND,
						TrickWeaponsAnimations.CHAKRAM_DUAL_AUTO1,
						TrickWeaponsAnimations.CHAKRAM_DUAL_AUTO2,
						TrickWeaponsAnimations.CHAKRAM_DUAL_AUTO3,
						TrickWeaponsAnimations.CHAKRAM_DUAL_DASH, TrickWeaponsAnimations.CHAKRAM_DUAL_AIRSLASH)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
				.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TrickWeaponCategories.CHAKRAM);

	};
	public static final Function<Item, CapabilityItem.Builder> RAPIER = (item) ->
			WeaponCapability.builder()
					.category(CapabilityItem.WeaponCategories.SWORD)
					.styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
					.collider(ColliderPreset.LONGSWORD)
					.swingSound(EpicFightSounds.WHOOSH.get())
					.hitSound(EpicFightSounds.BLADE_HIT.get())
					.canBePlacedOffhand(false)
					.innateSkill(CapabilityItem.Styles.TWO_HAND, (itemStack -> EpicFightSkills.TSUNAMI))
					.newStyleCombo(CapabilityItem.Styles.TWO_HAND, TrickWeaponsAnimations.RAPIER_AUTO1, TrickWeaponsAnimations.RAPIER_AUTO2, TrickWeaponsAnimations.RAPIER_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TrickWeaponsAnimations.HOLD_RAPIER)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TrickWeaponsAnimations.RAPIER_WALK)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD);
	public static final Function<Item, CapabilityItem.Builder> JOYEUSE = (item) ->
			WeaponCapability.builder()
					.styleProvider((playerpatch) -> {
						if (((PlayerPatch)playerpatch).getSkill(EpicFightSkills.SWORD_MASTER) != null)
						{
							return TrickWeaponsStyles.JOYEUSE;
						}
						return CapabilityItem.Styles.TWO_HAND;
					})
					.canBePlacedOffhand(false)
					.category(CapabilityItem.WeaponCategories.SWORD)
					.collider(ColliderPreset.LONGSWORD)
					.hitSound(EpicFightSounds.BLADE_HIT.get())
					.innateSkill(TrickWeaponsStyles.JOYEUSE, itemStack -> TrickWeaponsInnateSkills.FLARE_BLADE)
					.innateSkill(CapabilityItem.Styles.TWO_HAND, itemStack -> EpicFightSkills.SHARP_STAB)
					.newStyleCombo(TrickWeaponsStyles.JOYEUSE,
							TrickWeaponsAnimations.JOYEUSE_AUTO1,
							TrickWeaponsAnimations.JOYEUSE_AUTO2,
							TrickWeaponsAnimations.JOYEUSE_AUTO3,
							TrickWeaponsAnimations.JOYEUSE_DASH, Animations.LONGSWORD_AIR_SLASH)
					.newStyleCombo(CapabilityItem.Styles.TWO_HAND,
							Animations.LONGSWORD_AUTO1,
							Animations.LONGSWORD_AUTO2,
							Animations.LONGSWORD_AUTO3,
							Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
					.livingMotionModifier(TrickWeaponsStyles.JOYEUSE, LivingMotions.IDLE, TrickWeaponsAnimations.HOLD_JOYEUSE)
					.livingMotionModifier(TrickWeaponsStyles.JOYEUSE, LivingMotions.WALK, TrickWeaponsAnimations.JOYEUSE_WALK)
					.livingMotionModifier(TrickWeaponsStyles.JOYEUSE, LivingMotions.RUN, TrickWeaponsAnimations.JOYEUSE_RUN)
					.livingMotionModifier(TrickWeaponsStyles.JOYEUSE, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
	public static final Function<Item, CapabilityItem.Builder> SPELLBOOK = (item) ->
			WeaponCapability.builder()
					.category(TrickWeaponCategories.SPELLBOOK)
					.styleProvider((playerpatch) -> CapabilityItem.Styles.ONE_HAND)
					.canBePlacedOffhand(false)
					.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, TrickWeaponsAnimations.HOLD_SPELLBOOK);

	/**
	 * This Enum Class has the weapon categories
	 */
	public enum TrickWeaponCategories implements WeaponCategory {
		/**
		 * Rapiers are light and thin swords that are used for consecutive light quick thrust attacks with some minor slashes.
		 */
		RAPIER,
		/**
		 * Chakrams are small and lightweight throwing weapons that excel in ranged combat but can miss on close range encounters.
		 */
		CHAKRAM,
		/**
		 * PierceTH's Resource Pack on a Variant of Greatsword.
		 */
		SPELLBOOK;
		final int id;

		private TrickWeaponCategories()
		{
			this.id = WeaponCategory.ENUM_MANAGER.assign(this);
		}
		@Override
		public int universalOrdinal() {
			return this.id;
		}
	}

	private static final Map<String, Function<Item, CapabilityItem.Builder>> PRESETS = Maps.newHashMap();
	public static void register(WeaponCapabilityPresetRegistryEvent event) {
		Logger LOGGER = LogUtils.getLogger();
		LOGGER.info("Loading Weapon Capabilities");
		event.getTypeEntry().put("chakram", CHAKRAM);
		event.getTypeEntry().put("joyeuse", JOYEUSE);
		event.getTypeEntry().put("spellbook", SPELLBOOK);
		event.getTypeEntry().put("rapier", RAPIER);
		LOGGER.info("Weapon Capabilities Loaded");
	}

	public static Function<Item, CapabilityItem.Builder> get(String typeName) {
		return PRESETS.get(typeName);
	}
}
