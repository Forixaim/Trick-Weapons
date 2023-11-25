package net.forixaim.trick_weapons.bridgeassets;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.forixaim.trick_weapons.TrickWeapons;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.SourceTags;

import java.util.List;
import java.util.Random;
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
	public static StaticAnimation RAPIER_AUTO2;
	public static StaticAnimation RAPIER_AUTO3;
	public static StaticAnimation RAPIER_AUTO4;
	public static StaticAnimation GREATSWORD_VARIANT_DASH;
	public static StaticAnimation GREATSWORD_VARIANT_BASIC_RUN;
	public static StaticAnimation HOLD_GREATSWORD_VARIANT;
	public static StaticAnimation GREATSWORD_VARIANT_AIRSLASH;
	public static StaticAnimation HOLD_SPELLBOOK;
	public static StaticAnimation RAPIER_WALK;
	public static StaticAnimation QUICK_RIPOSTE;
	public static StaticAnimation HOLD_JOYEUSE;
	public static StaticAnimation JOYEUSE_AUTO1;
	public static StaticAnimation JOYEUSE_AUTO2;
	public static StaticAnimation JOYEUSE_WALK;
	public static StaticAnimation JOYEUSE_AUTO3;
	public static StaticAnimation FLARE_BLADE;
	public static StaticAnimation JOYEUSE_MOUNT_AUTO1;
	public static StaticAnimation JOYEUSE_RUN;
	public static StaticAnimation JOYEUSE_MOUNT;
	public static StaticAnimation JOYEUSE_DASH;

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
		GREATSWORD_VARIANT_AIRSLASH = new AirSlashAnimation(0.1F, 0.5F, 0.75F, 1.21F, 0.75F, false, null, Biped.toolR, "greatsword_variant/greatsword_airslash", Biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.FINISHER))
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.6f))
				.addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(2.5F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Object[]{new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.toolR, 1.1, 0.55F})});;
		HOLD_RAPIER = new StaticAnimation(true, "rapier/hold_rapier", Biped);
		RAPIER_WALK = new MovementAnimation(true, "rapier/rapier_walk", Biped);
		RAPIER_AUTO1 = new BasicAttackAnimation(0.1f, 0.0f, 0.3f, 0.7f, 0.4f, ColliderPreset.LONGSWORD, Biped.toolR, "rapier/rapier_auto1", Biped);
		HOLD_SPELLBOOK = new StaticAnimation(true, "spellbook/hold_spellbook", Biped);
		RAPIER_AUTO2 = new BasicAttackAnimation(0.1f, 0.0f, 0.3f, 0.7f, 0.4f, ColliderPreset.LONGSWORD, Biped.toolR, "rapier/rapier_auto2", Biped);
		RAPIER_AUTO3 = new BasicAttackAnimation(0.1f, 0.0f, 0.3f, 0.7f, 0.4f, ColliderPreset.LONGSWORD, Biped.toolR, "rapier/rapier_auto3", Biped);
		RAPIER_AUTO4 = new BasicAttackAnimation(0.1f, 0.0f, 0.3f, 0.7f, 0.4f, ColliderPreset.LONGSWORD, Biped.toolR, "rapier/rapier_auto4", Biped);
		QUICK_RIPOSTE = new AttackAnimation(0.1F, 0.0F, 0.6F, 1.2F, 1.8F, ColliderPreset.LONGSWORD, Biped.toolR, "rapier/quick_riposte", Biped);
		HOLD_JOYEUSE = new StaticAnimation(true, "joyeuse/hold_joyeuse", Biped);
		JOYEUSE_WALK = new MovementAnimation(true, "joyeuse/joyeuse_walk", Biped);
		JOYEUSE_AUTO1 = new BasicAttackAnimation(0.1f, 0.1f, 0.9f, 1.5f, 1.7f, ColliderPreset.LONGSWORD, Biped.toolR, "joyeuse/joyeuse_auto1", Biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elaspedtime) -> 3.5f);
		JOYEUSE_AUTO2 = new BasicAttackAnimation(0.1f, 0.1f, 0.6f, 1.5f, 1.5f, ColliderPreset.LONGSWORD, Biped.toolR, "joyeuse/joyeuse_auto2", Biped)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elaspedtime) -> 3.5f);
		JOYEUSE_AUTO3 = new AttackAnimation(0.1f, 0.1f, 0.3f, 1.5f, 6.7f, ColliderPreset.LONGSWORD, Biped.toolR, "joyeuse/joyeuse_auto3", Biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(2))
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 6.5f))
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elaspedtime) -> 3.5f);
		FLARE_BLADE = new AttackAnimation(0.1f, "joyeuse/joyeuse_flare_blade", Biped,
				new Phase(0.0f, 0.1f, 0.0f, 0.0f, 4.5f,3.1f, Biped.rootJoint, ColliderPreset.DAGGER),
				new Phase(3.1f, 0.1f, 3.5f, 4.5f, 4.5f,4.5f, Biped.rootJoint, TrickWeaponsColliders.FLARE_BLITZ),
				new Phase(4.5f, 0.1f, 4.6f, 5.1f, 5.1f, 5.1f, Biped.rootJoint, TrickWeaponsColliders.FLARE_BLITZ),
				new Phase(5.1f, 0.1f, 5.2f, 6.1f, 6.1f, 6.1f, Biped.rootJoint, TrickWeaponsColliders.FLARE_BLITZ),
				new Phase(6.1f, 0.1f, 6.2f, 7.1f, 7.1f, 7.1f, Biped.rootJoint, TrickWeaponsColliders.FLARE_BLITZ),
				new Phase(7.1f, 0.1f, 7.2f, 8.1f, 8.1f, 8.1f, Biped.rootJoint, TrickWeaponsColliders.FLARE_BLITZ)
		)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.BLAZE_SHOOT)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5f)
				.addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
				.addEvents(
						AnimationEvent.TimeStampedEvent.create(0.1F, (entitypatch, animation, params) -> {
							Entity entity = entitypatch.getOriginal();
							RandomSource random = entitypatch.getOriginal().getRandom();
							double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							double y = entity.getY();
							double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							for (int i = 0; i < 500; i++)
							{
								entity.level().addParticle(ParticleTypes.FLAME, random.nextDouble() * 10.0D + x - ((random.nextDouble() * 10.0D)/2), random.nextDouble() * 10.0D  + y, random.nextDouble() * 10.0D + z - ((random.nextDouble() * 10.0D)/2), random.nextDouble() * 0.5D, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D);
							}
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(3.3F, (entitypatch, animation, params) -> {
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
							RandomSource random = entitypatch.getOriginal().getRandom();
							double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							double y = entity.getY();
							double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							for (int i = 0; i < 100; i++)
							{
								entity.level().addParticle(ParticleTypes.FLAME, random.nextDouble() * 0.5 + x, random.nextDouble() * 2.0  + y, random.nextDouble() * 0.5 + z, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D);
							}
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(3.9F, (entitypatch, animation, params) -> {
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
							RandomSource random = entitypatch.getOriginal().getRandom();
							double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							double y = entity.getY();
							double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							for (int i = 0; i < 100; i++)
							{
								entity.level().addParticle(ParticleTypes.FLAME, random.nextDouble() * 0.5 + x, random.nextDouble() * 2.0  + y, random.nextDouble() * 0.5 + z, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D);

							}
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(4.5F, (entitypatch, animation, params) -> {
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
							RandomSource random = entitypatch.getOriginal().getRandom();
							double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							double y = entity.getY();
							double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							for (int i = 0; i < 100; i++)
							{
								entity.level().addParticle(ParticleTypes.FLAME, random.nextDouble() * 0.5 + x, random.nextDouble() * 2.0  + y, random.nextDouble() * 0.5 + z, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D);

							}
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(5.1F, (entitypatch, animation, params) -> {
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
							RandomSource random = entitypatch.getOriginal().getRandom();
							double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							double y = entity.getY();
							double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							for (int i = 0; i < 100; i++)
							{
								entity.level().addParticle(ParticleTypes.FLAME, random.nextDouble() * 0.5 + x, random.nextDouble() * 2.0  + y, random.nextDouble() * 0.5 + z, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D);

							}
							}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(6.1F, (entitypatch, animation, params) -> {
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
							RandomSource random = entitypatch.getOriginal().getRandom();
							double x = entity.getX() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							double y = entity.getY();
							double z = entity.getZ() + (random.nextDouble() - random.nextDouble()) * 2.0D;
							for (int i = 0; i < 100; i++)
							{
								entity.level().addParticle(ParticleTypes.FLAME, random.nextDouble() * 0.5 + x, random.nextDouble() * 2.0  + y, random.nextDouble() * 0.5 + z, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D, random.nextDouble() * 0.5D);

							}
							}, AnimationEvent.Side.CLIENT)
				);

		JOYEUSE_RUN = new MovementAnimation(true, "joyeuse/joyeuse_run", Biped);
		JOYEUSE_MOUNT_AUTO1 = new AttackAnimation(0.1F, 0.5F, 1.6F, 3.25F, 9.7F, ColliderPreset.LONGSWORD, Biped.toolR, "joyeuse/joyeuse_mount_auto1", Biped);
		JOYEUSE_MOUNT = new StaticAnimation(true, "joyeuse/joyeuse_mount", Biped);
		JOYEUSE_DASH = new DashAttackAnimation(0.1f, 0.1f, 0.6f, 1.5f, 1.5f, ColliderPreset.LONGSWORD, Biped.toolR, "joyeuse/joyeuse_dash_attack", Biped)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, elaspedtime) -> 3.5f);
	}
}