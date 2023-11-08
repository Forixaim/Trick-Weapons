package net.forixaim.trick_weapons.bridgeassets;

import com.electronwill.nightconfig.core.Config;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.forixaim.trick_weapons.TrickWeapons;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.world.damagesource.SourceTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Set;

//convertTime: Startup
//antic: Idk
//contact: Active Frames
//recovery: Endlag

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
	 * Throws the right chakram.
	 */
	public static StaticAnimation CHAKRAM_DUAL_AUTO1;
	/**
	 * Throws the left chakram.
	 */
	public static StaticAnimation CHAKRAM_DUAL_AUTO2;
	/**
	 * Throws both chakrams.
	 */
	public static StaticAnimation CHAKRAM_DUAL_AUTO3;
	/**
	 * Same as Auto 3 but with far longer reach
	 */
	public static StaticAnimation CHAKRAM_DUAL_DASH;
	/**
	 * Spin like a beyblade.
	 */
	public static StaticAnimation CHAKRAM_DUAL_AIRSLASH;
	/**
	 * After a quick startup, spin around orbiting your chakrams twice before returning to your hands.
	 */
	public static StaticAnimation SPINNING_WHIRLWIND;
	public static StaticAnimation HOLD_RAPIER;
	public static StaticAnimation RAPIER_AUTO1;
	public static StaticAnimation GREATSWORD_VARIANT_DASH;
	public static StaticAnimation GREATSWORD_VARIANT_BASIC_RUN;
	public static StaticAnimation HOLD_GREATSWORD_VARIANT;
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
		CHAKRAM_AUTO2 = new BasicAttackAnimation(0.1f, 0.0f, 0.3f, 1.2f, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_auto2", Biped);
		CHAKRAM_DASH = new DashAttackAnimation(0.05F, 0.1F, 0.05F, 0.7F, 0.65F, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_dash", Biped);
		CHAKRAM_AIRSLASH = new AirSlashAnimation(0.1f, 0.0f, 0.3f, 0.4f, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_auto2", Biped);
		PRECISION_VERTICAL = new AttackAnimation(0.1F, 0.0F, 0.15F, 1.2F, 1.8F, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_precision_vertical", Biped);
		CHAKRAM_DUAL_AUTO1 = new BasicAttackAnimation(0.2f, 0.0f, 0.6f, 1.2f, ColliderPreset.DAGGER, Biped.toolR, "chakram/chakram_dual_auto1", Biped);
		CHAKRAM_DUAL_AUTO2 = new BasicAttackAnimation(0.2f, 0.0f, 0.6f, 1.2f, ColliderPreset.DAGGER, Biped.toolL, "chakram/chakram_dual_auto2", Biped);
		CHAKRAM_DUAL_AUTO3 = new BasicAttackAnimation(0.1F, "chakram/chakram_dual_auto3", Biped,
				new Phase(0.0F, 0.25F, 0.05F, 0.7F, 0.6F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, List.of(Pair.of(Biped.toolR, null), Pair.of(Biped.toolL, null))));
		CHAKRAM_DUAL_DASH = new DashAttackAnimation(0.1F, "chakram/chakram_dual_dash", Biped,
				new Phase(0.0F, 0.25F, 0.05F, 0.7F, 0.6F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, List.of(Pair.of(Biped.toolR, null), Pair.of(Biped.toolL, null))));
		CHAKRAM_DUAL_AIRSLASH = new AirSlashAnimation(0.05f, 0.0f, 0.6f, 2.6f, ColliderPreset.LONGSWORD, Biped.legR, "chakram/chakram_dual_airslash", Biped)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.3f))
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
		SPINNING_WHIRLWIND = new AttackAnimation(0.2f, "chakram/chakram_spinning_whirlwind", Biped,
				new Phase(0.0F, 0.0F, 0.1F, 1.7F, 1.6F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, List.of(Pair.of(Biped.toolR, null), Pair.of(Biped.toolL, null))))
				.addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
		GREATSWORD_VARIANT_DASH = (new DashAttackAnimation(0.2F, 0.2F, 1.1F, 2.1F, 1.7F, (Collider)null, Biped.toolR, "greatsword_variant/greatsword_dash", Biped, false))
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.FINISHER))
				.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
				.addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.8F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Object[]{new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.toolR, 1.1, 0.55F})});
		GREATSWORD_VARIANT_BASIC_RUN = new MovementAnimation(true, "greatsword_variant/run_greatsword", Biped);
		HOLD_GREATSWORD_VARIANT = new StaticAnimation(true, "greatsword_variant/hold_greatsword", Biped);
	}
}