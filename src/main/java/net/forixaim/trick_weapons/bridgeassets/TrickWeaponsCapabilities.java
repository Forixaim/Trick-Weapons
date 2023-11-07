package net.forixaim.trick_weapons.bridgeassets;

import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

/**
 * @brief This class is used to register the core weapon capabilities
 */
public class TrickWeaponsCapabilities {
	/**
	 * Placeholder animations
	 * Rapiers are light and thin swords that are used for consecutive light quick thrust attacks with some minor slashes.
	 */
	public static final Function<Item, CapabilityItem.Builder> RAPIER = (item) -> {
		return WeaponCapability.builder()
				.styleProvider((playerpatch) -> {
					return TrickWeaponsStyles.RAPIER;
				})
				.category(TrickWeaponCategories.RAPIER)
				.innateSkill(TrickWeaponsStyles.RAPIER, itemStack -> EpicFightSkills.SWEEPING_EDGE)
				.newStyleCombo(TrickWeaponsStyles.RAPIER,
						Animations.SWORD_DASH,
						Animations.EVISCERATE_FIRST,
						Animations.TRIDENT_AUTO1,
						Animations.LONGSWORD_DASH,
						Animations.SHARP_STAB, Animations.LONGSWORD_DASH, Animations.SWORD_AIR_SLASH);
	};

	/**
	 * Chakram Animations with inspirations from Better Combat and Simply Swords
	 * Chakrams are small and lightweight throwing weapons that excel in ranged combat but can miss on close range encounters.
	 */
	public static final Function<Item, CapabilityItem.Builder> CHAKRAM = (item) -> {
		return WeaponCapability.builder()
				.styleProvider((playerpatch) ->
						playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == TrickWeaponCategories.CHAKRAM ? TrickWeaponsStyles.DUAL_CHAKRAMS : TrickWeaponsStyles.CHAKRAM)
				.category(TrickWeaponCategories.CHAKRAM)
				.innateSkill(TrickWeaponsStyles.CHAKRAM, itemStack -> TrickWeaponsInnateSkills.PRECISION_VERTICAL)
				.innateSkill(TrickWeaponsStyles.DUAL_CHAKRAMS, itemStack -> TrickWeaponsInnateSkills.SPINNING_WHIRLWIND)
				.newStyleCombo(TrickWeaponsStyles.CHAKRAM,
						TrickWeaponsAnimations.CHAKRAM_AUTO1,
						TrickWeaponsAnimations.CHAKRAM_AUTO2,
						TrickWeaponsAnimations.CHAKRAM_DASH, TrickWeaponsAnimations.CHAKRAM_AIRSLASH)
				.newStyleCombo(TrickWeaponsStyles.DUAL_CHAKRAMS,
						TrickWeaponsAnimations.CHAKRAM_DUAL_AUTO1,
						TrickWeaponsAnimations.CHAKRAM_DUAL_AUTO2,
						TrickWeaponsAnimations.CHAKRAM_DUAL_AUTO3,
						TrickWeaponsAnimations.CHAKRAM_DUAL_DASH, TrickWeaponsAnimations.CHAKRAM_DUAL_AIRSLASH)
				.livingMotionModifier(TrickWeaponsStyles.CHAKRAM, LivingMotions.IDLE, Animations.BIPED_IDLE)
				.swingSound(EpicFightSounds.WHOOSH_SMALL)
				.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == TrickWeaponCategories.CHAKRAM);
	};

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
		CHAKRAM;
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

	public static void register(WeaponCapabilityPresetRegistryEvent event){
		Logger LOGGER = LogUtils.getLogger();
		LOGGER.info("Loading Weapon Capabilities");
		event.getTypeEntry().put("rapier", RAPIER);
		event.getTypeEntry().put("chakram", CHAKRAM);
		LOGGER.info("Weapon Capabilities Loaded");
	}
}
