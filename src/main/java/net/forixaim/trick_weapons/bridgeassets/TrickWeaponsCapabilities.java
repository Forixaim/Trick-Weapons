package net.forixaim.trick_weapons.bridgeassets;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
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
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
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

	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_VARIANT = (item) ->
			WeaponCapability.builder()
					.category(TrickWeaponCategories.GREATSWORD_VARIANT)
					.styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
					.collider(ColliderPreset.GREATSWORD)
					.swingSound(EpicFightSounds.WHOOSH_BIG.get())
					.hitSound(EpicFightSounds.BLADE_HIT.get())
					.canBePlacedOffhand(false)
					.newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, TrickWeaponsAnimations.GREATSWORD_VARIANT_DASH, Animations.GREATSWORD_AIR_SLASH)
					.innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TrickWeaponsAnimations.HOLD_GREATSWORD_VARIANT)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TrickWeaponsAnimations.GREATSWORD_VARIANT_BASIC_RUN)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLY, Animations.BIPED_HOLD_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CREATIVE_FLY, Animations.BIPED_HOLD_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CREATIVE_IDLE, Animations.BIPED_HOLD_GREATSWORD)
					.livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

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
		GREATSWORD_VARIANT;
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
		event.getTypeEntry().put("greatsword_variant",GREATSWORD_VARIANT);
		LOGGER.info("Weapon Capabilities Loaded");
	}

	public static Function<Item, CapabilityItem.Builder> get(String typeName) {
		return PRESETS.get(typeName);
	}
}
