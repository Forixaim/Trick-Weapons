package net.forixaim.trick_weapons.bridgeassets;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

/**
 * @brief This class is used to register capabilities
 */
public class TrickWeaponsCapabilities {
	/**
	 * @brief Placeholder animations
	 */
	public static final Function<Item, CapabilityItem.Builder> RAPIER = (item) -> {
		return WeaponCapability.builder()
				.styleProvider((playerpatch) -> {
					return TrickWeaponsStyles.RAPIER;
				})
				.category(ProjectOmneriaWeaponCategories.RAPIER)
				.innateSkill(TrickWeaponsStyles.RAPIER, itemStack -> EpicFightSkills.SWEEPING_EDGE)
				.newStyleCombo(TrickWeaponsStyles.RAPIER,
						Animations.SWORD_DASH,
						Animations.EVISCERATE_FIRST,
						Animations.TRIDENT_AUTO1,
						Animations.LONGSWORD_DASH,
						Animations.SHARP_STAB, Animations.LONGSWORD_DASH, Animations.SWORD_AIR_SLASH);
	};

	/**
	 * @brief Chakram Animations with inspirations from Better Combat and Simply Swords
	 */
	public static final Function<Item, CapabilityItem.Builder> CHAKRAM = (item) -> {
		return WeaponCapability.builder()
				.styleProvider((playerpatch) ->
				{
					return TrickWeaponsStyles.CHAKRAM;
				})
				.category(ProjectOmneriaWeaponCategories.CHAKRAM)
				.innateSkill(TrickWeaponsStyles.CHAKRAM, itemStack -> EpicFightSkills.SWEEPING_EDGE)
				.newStyleCombo(TrickWeaponsStyles.CHAKRAM,
						TrickWeaponsAnimations.CHAKRAM_AUTO1,
						TrickWeaponsAnimations.CHAKRAM_AUTO2,
						TrickWeaponsAnimations.CHAKRAM_DASH, Animations.DAGGER_AIR_SLASH)
				.livingMotionModifier(TrickWeaponsStyles.CHAKRAM, LivingMotions.IDLE, Animations.BIPED_IDLE)
				.swingSound(EpicFightSounds.WHOOSH_SMALL);
	};

	public enum ProjectOmneriaWeaponCategories implements WeaponCategory {
		RAPIER,
		CHAKRAM;
		final int id;

		private ProjectOmneriaWeaponCategories()
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
