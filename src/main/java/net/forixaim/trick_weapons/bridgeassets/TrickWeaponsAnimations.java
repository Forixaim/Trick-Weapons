package net.forixaim.trick_weapons.bridgeassets;

import com.mojang.logging.LogUtils;
import net.forixaim.trick_weapons.TrickWeapons;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

public class TrickWeaponsAnimations
{
	/**
	 * A stationary chakram throw with swinging motions from left to right.
	 */
	public static StaticAnimation CHAKRAM_AUTO1;
	/**
	 * A stationary chakram throw with swinging motions from bottom right to top left.
	 */
	public static StaticAnimation CHAKRAM_AUTO2;
	/**
	 * Dash forward and throw the chakram in a considerable distance ahead of you. Will miss on opponents too close to you.
	 */
	public static StaticAnimation CHAKRAM_DASH;
	/**
	 * Same as second auto but in the air. Currently couldn't think of a new original one.
	 */
	public static StaticAnimation CHAKRAM_AIRSLASH;
	/**
	 * Throws the chakram a long distance in a stright line ahead of you then returning it back.
	 */
	public static StaticAnimation PRECISION_VERTICAL;

	/**
	 * Registers the initialized animations.
	 * @param Event The Forge Event currently used by Epic Fight's API
	 */
	public static void RegisterAnimations(AnimationRegistryEvent Event)
	{
		Logger LOGGER = LogUtils.getLogger();
		LOGGER.info("Team Forixaim: Loading Animations!");
		Event.getRegistryMap().put(TrickWeapons.MOD_ID, TrickWeaponsAnimations::Initialize);
	}

	/**
	 * Initializes the EpicFight Animations, binding proper EpicFight JSON animation files to the StaticAnimation classes.
	 */
	private static void Initialize()
	{
		HumanoidArmature Biped = Armatures.BIPED;
		Logger LOGGER = LogUtils.getLogger();
		LOGGER.info("Team Forixaim: Initialization in Progress!");
		CHAKRAM_AUTO1 = new BasicAttackAnimation(0.1f, 0.0f, 0.3f, 0.4f, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_auto1", Biped);
		CHAKRAM_AUTO2 = new BasicAttackAnimation(0.1f, 0.0f, 0.3f, 0.4f, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_auto2", Biped);
		CHAKRAM_DASH = new DashAttackAnimation(0.1F, 0.1F, 0.1F, 0.2F, 0.65F, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_dash", Biped);
		CHAKRAM_AIRSLASH = new AirSlashAnimation(0.1f, 0.0f, 0.3f, 0.4f, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_auto2", Biped);
		PRECISION_VERTICAL = new AttackAnimation(0.1F, 0.0F, 0.15F, 0.3F, 0.8F, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_precision_vertical", Biped);
	}
}
