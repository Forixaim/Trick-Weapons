package net.forixaim.epic_fight_battle_styles.core_assets.animations;


import dev.xylonity.explosiveenhancement.ExplosiveEnhancement;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.balancing.ChakramBalancing;
import net.forixaim.epic_fight_battle_styles.core_assets.colliders.ChakramColliders;
import net.forixaim.epic_fight_battle_styles.core_assets.colliders.HeroSwordColliders;
import net.forixaim.epic_fight_battle_styles.core_assets.colliders.LumiereColliders;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.skill.WOMSkillDataKeys;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.*;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.*;

import java.util.Set;

/**
 * Chakram Animation Module,
 */
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BattleAnimations extends BattleStylesAnimation
{
	//Base Rapier Animations
	public static StaticAnimation RAPIER_IDLE;
	public static StaticAnimation RAPIER_WALK;
	public static StaticAnimation RAPIER_AUTO1;
	public static StaticAnimation RAPIER_AUTO2;
	//Imperatrice Lumiere Sword
	public static StaticAnimation IMPERATRICE_JOYEUSE_DRAW;
	public static StaticAnimation IMPERATRICE_SWORD_EN_GARDE;
	public static StaticAnimation IMPERATRICE_SWORD_IDLE_FLAIR;
	public static StaticAnimation IMPERATRICE_SWORD_CROUCH;
	public static StaticAnimation IMPERATRICE_SWORD_CROUCH_WALK;
	public static StaticAnimation IMPERATRICE_SWORD_WALK;
	public static StaticAnimation IMPERATRICE_SWORD_RUN;
	public static StaticAnimation IMPERATRICE_SWORD_JUMP;
	//Imperatrice Attacks
	public static StaticAnimation IMPERATRICE_SWORD_JAB1;
	public static StaticAnimation IMPERATRICE_SWORD_JAB2;
	public static StaticAnimation IMPERATRICE_SWORD_JAB3;
	public static StaticAnimation IMPERATRICE_SWORD_DOWN_SMASH;
	public static StaticAnimation IMPERATRICE_SWORD_FLARE_BURST;
	public static StaticAnimation IMPERATRICE_SWORD_FTILT;
	public static StaticAnimation IMPERATRICE_SWORD_CERCLE_DE_FEU;
	public static StaticAnimation IMPERATRICE_SWORD_RTILT;
	public static StaticAnimation IMPERATRICE_SWORD_BTILT;
	public static StaticAnimation IMPERATRICE_SWORD_LTILT;
	public static StaticAnimation IMPERATRICE_SWORD_FORWARD_AERIAL;
	public static StaticAnimation IMPERATRICE_SWORD_DASH_ATTACK;
	public static StaticAnimation IMPERATRICE_SWORD_BLAZE_STINGER;
	public static StaticAnimation IMPERATRICE_SWORD_CROUCH_LIGHT;
	public static StaticAnimation IMPERATRICE_SWORD_NEUTRAL_AERIAL;
	public static StaticAnimation IMPERATRICE_SWORD_NEUTRAL_HEAVY_AERIAL;
	//Dash Attack
	public static StaticAnimation IMPERATRICE_SWORD_INFERNAL_WHEEL;
	//Trailblaze
	public static StaticAnimation IMPERATRICE_TRAILBLAZE_FWD;
	public static StaticAnimation IMPERATRICE_TRAILBLAZE_LEFT;
	public static StaticAnimation IMPERATRICE_TRAILBLAZE_RIGHT;
	public static StaticAnimation IMPERATRICE_TRAILBLAZE_BACK;
	public static StaticAnimation IMPERATRICE_TRAILBLAZE_SPOT;
	//Combat Arts
	public static StaticAnimation IMPERATRICE_BLAZE_BLASTER;
	public static StaticAnimation IMPERATRICE_PROMINENCE_BLAZE;
	public static StaticAnimation IMPERATRICE_ULTIMATE_TRY;
	public static StaticAnimation IMPERATRICE_FLARE_BLADE_CLEAVE;
	// Hero Sword and Shield
	public static StaticAnimation HERO_SWORD_IDLE;
	public static StaticAnimation HERO_SWORD_AUTO_1;
	public static StaticAnimation HERO_SHIELD_AUTO_1;
	public static StaticAnimation HERO_SWORD_AUTO_2;
	public static StaticAnimation HERO_SHIELD_AUTO_2;
	public static StaticAnimation HERO_SWORD_AUTO_3;
	public static StaticAnimation HERO_SWORD_GUARD;
	public static StaticAnimation HERO_SWORD_GUARD_HIT;
	public static StaticAnimation HERO_SHIELD_BLOCK;
	public static StaticAnimation HERO_SHIELD_BLOCK_HIT;
	public static StaticAnimation SLAMMING_HERO;

	//Default Chakram
	public static StaticAnimation SINGLE_CHAKRAM_IDLE;
	public static StaticAnimation SINGLE_CHAKRAM_WALK;
	//Combat Motions
	public static StaticAnimation SINGLE_CHAKRAM_AUTO_1;
	public static StaticAnimation SINGLE_CHAKRAM_AUTO_2;
	public static StaticAnimation SINGLE_CHAKRAM_AIR_SLASH;
	public static StaticAnimation SINGLE_CHAKRAM_DASH_ATTACK;
	//Innate Skills (Now Renamed to Charge Attacks)
	public static StaticAnimation PRECISION_VERTICAL;

	//House Lux Arms Master Greatsword/Excalibur
	public static StaticAnimation HOUSE_LUX_GS_EXCALIBUR_IDLE;

	//Combat Arts
	public static StaticAnimation BLAZE_WHEEL;

	@Override
	protected void Build()
	{
		HumanoidArmature biped = Armatures.BIPED;
		//Rapier Animations
		RAPIER_IDLE = new StaticAnimation(true, "rapier/idle", biped);
		RAPIER_WALK = new MovementAnimation(true, "rapier/walk", biped);
		RAPIER_AUTO1 = new BasicAttackAnimation(0.0f, 0.0f, 0.3f, 0.7f, 1.0f, null, biped.toolR, "rapier/auto1", biped);
		RAPIER_AUTO2 = new BasicAttackAnimation(0.0f, 0.0f, 0.3f, 0.7f, 1.0f, null, biped.toolR, "rapier/auto2", biped);
		//Imperatrice Lumiere Style (All animations aren't affected by attack speed)
		IMPERATRICE_JOYEUSE_DRAW = new AttackAnimation(0.0f, 0.00f, 0.00f, 0.0f, 1.2f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/joyeuse_draw", biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addEvents(AnimationEvent.TimeStampedEvent.create(0.85f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.BLAZE_SHOOT));
		IMPERATRICE_SWORD_EN_GARDE = new StaticAnimation(true, "battle_style/legendary/imperatrice_lumiere/sword/idle", biped)
				.addState(EntityState.CAN_BASIC_ATTACK, true);
		IMPERATRICE_SWORD_JUMP = new StaticAnimation(false, "battle_style/legendary/imperatrice_lumiere/sword/movement/neutral_jump", biped);

		IMPERATRICE_SWORD_CROUCH = new StaticAnimation(true, "battle_style/legendary/imperatrice_lumiere/sword/crouch", biped)
				.addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true);
		IMPERATRICE_SWORD_CROUCH_WALK = new MovementAnimation(true, "battle_style/legendary/imperatrice_lumiere/sword/crouch_walk", biped);
		IMPERATRICE_SWORD_WALK = new MovementAnimation(true, "battle_style/legendary/imperatrice_lumiere/sword/walk", biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a,b,c,d,e) -> 1.5f);
		IMPERATRICE_SWORD_RUN = new MovementAnimation(true, "battle_style/legendary/imperatrice_lumiere/sword/run", biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a,b,c,d,e) -> 1.5f);
		IMPERATRICE_SWORD_IDLE_FLAIR = new StaticAnimation(false, "battle_style/legendary/imperatrice_lumiere/sword/idle_flair_1", biped);
		IMPERATRICE_SWORD_JAB1 = new BasicAttackAnimation(0.05f, 0.25f, 0.4f, 0.5f, 1f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/jab1", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.1f))
				.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) ->
				{
					float baseSpd = 2.2f;
					if (livingEntityPatch instanceof PlayerPatch<?> playerPatch)
					{
						if (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get())))
						{
							return baseSpd * 1.1f;
						}

					}
					return baseSpd;
				})
				.addState(EntityState.TURNING_LOCKED, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true);
		IMPERATRICE_SWORD_JAB2 = new BasicAttackAnimation(0.05f, 0.25f, 0.5f, 0.6f, 1f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/jab2", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING2.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.1f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) ->
				{
					float baseSpd = 2.2f;
					if (livingEntityPatch instanceof PlayerPatch<?> playerPatch)
					{
						if (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get())))
						{
							return baseSpd * 1.1f;
						}

					}
					return baseSpd;
				})				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);
		IMPERATRICE_SWORD_JAB3 = new BasicAttackAnimation(0.05f, 0.05f, 0.5f, 0.75f, 2f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/jab3", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING3.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_M.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) ->
				{
					float baseSpd = 2.2f;
					if (livingEntityPatch instanceof PlayerPatch<?> playerPatch)
					{
						if (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get())))
						{
							return baseSpd * 1.1f;
						}

					}
					return baseSpd;
				})				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);

		IMPERATRICE_ULTIMATE_TRY = new AttackAnimation(0.05f, 0.0f, 0.85f, 1f, 5f, LumiereColliders.IMPERATRICE_ULTIMATE_TRY, biped.rootJoint, "battle_style/legendary/imperatrice_lumiere/sword/ultimate_arts/try", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.GENERIC_EXPLODE)
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(500f))
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1))
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.GUARD_PUNCTURE, DamageTypeTags.BYPASSES_INVULNERABILITY, EpicFightDamageType.WEAPON_INNATE))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addEvents(AnimationEvent.TimeStampedEvent.create(0.85f, (livingEntityPatch, staticAnimation, objects) -> {
					livingEntityPatch.playSound(SoundRegistry.IMPERATRICE_FLASH.get(), 1, 0,0);
				}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.85f, AnimationHelpers.EXPLODE_DIRECTION, AnimationEvent.Side.CLIENT).params(5f, 4f));

		IMPERATRICE_SWORD_NEUTRAL_HEAVY_AERIAL = new AttackAnimation(0.0f, "battle_style/legendary/imperatrice_lumiere/sword/neutral_heavy_aerial", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.85f, 0.9f, 2f, 2f, biped.rootJoint, LumiereColliders.IMPERATRICE_FLAMING_ATMOSPHERE))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING3.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_FINISHER.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2f))
				.addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 2f))
				.addState(EntityState.TURNING_LOCKED, true)
				.addState(EntityState.LOCKON_ROTATE, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d, e) -> 1.5f)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addEvents(AnimationEvent.TimeStampedEvent.create(0.0f, ((livingEntityPatch, staticAnimation, objects) ->
				{
					livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 0, false, false, false));
				}), AnimationEvent.Side.CLIENT));

		IMPERATRICE_FLARE_BLADE_CLEAVE = new AttackAnimation(0f, 0f, 2.85f, 3f, 10f, LumiereColliders.IMPERATRICE_FLARE_BLADE_CLEAVE, biped.rootJoint, "battle_style/legendary/imperatrice_lumiere/sword/ultimate_arts/flare_blade_cleave", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(500))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NEUTRALIZE)
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.BYPASS_DODGE, EpicFightDamageType.GUARD_PUNCTURE))
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_HEAVY_SWING.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_FINISHER.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(500f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true)
				.addEvents(AnimationEvent.TimeStampedEvent.create(3.5f, AnimationHelpers.EXPLODE_DIRECTION, AnimationEvent.Side.CLIENT).params(20f, 10f));
		IMPERATRICE_SWORD_DOWN_SMASH = new AttackAnimation(0.05f, 0.4f, 0.4f, 0.5f, 1.25f, LumiereColliders.IMPERATRICE_DOWN_SMASH, biped.rootJoint, "battle_style/legendary/imperatrice_lumiere/sword/down_smash", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.GENERIC_EXPLODE)
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true)
				.addEvents(
						AnimationEvent.TimeStampedEvent.create(0.4F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							if (ModList.get().isLoaded(ExplosiveEnhancement.MODID))
							{
								entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 3f, Level.ExplosionInteraction.NONE);
							}
							else
								entity.level().addParticle(ParticleTypes.EXPLOSION_EMITTER, entity.getX(), entity.getY() + 1, entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);

						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.4f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.0F, -2.0F), Armatures.BIPED.legL, 3D, 0.55F));
		IMPERATRICE_SWORD_FLARE_BURST = new AttackAnimation(0.2f, 1.1f, 1.1f, 1.2f, 2f, LumiereColliders.IMPERATRICE_FLARE_BURST, biped.rootJoint, "battle_style/legendary/imperatrice_lumiere/sword/burst_art/flare_burst", biped)
			.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(19))
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(DamageTypeTags.BYPASSES_ENCHANTMENTS, DamageTypeTags.BYPASSES_RESISTANCE, DamageTypeTags.BYPASSES_SHIELD, DamageTypeTags.BYPASSES_ARMOR, EpicFightDamageType.GUARD_PUNCTURE))
			.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
			.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.BLAZE_SHOOT)
			.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.GENERIC_EXPLODE)
			.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
			.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true)
				.addEvents(
						AnimationEvent.TimeStampedEvent.create(1.1F, (entitypatch, animation, params) ->
						{
							entitypatch.playSound(SoundRegistry.FLARE_BURST_AURA.get(), 0, 0);
							Entity entity = entitypatch.getOriginal();
							if (ModList.get().isLoaded(ExplosiveEnhancement.MODID))
							{
								entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 4f, Level.ExplosionInteraction.NONE);
							}
							else
								entity.level().addParticle(ParticleTypes.EXPLOSION_EMITTER, entity.getX(), entity.getY() + 1, entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
							if (entitypatch instanceof PlayerPatch<?> playerPatch && playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.HEAT.get()))
							{
								if (!playerPatch.isLogicalClient())
								{
									playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.HEAT.get(), 1000, (ServerPlayer) playerPatch.getOriginal());
									playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.FLARE_BURST.get(), true, (ServerPlayer) playerPatch.getOriginal());
									if (ModList.get().isLoaded("wom"))
									{
										if (playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).hasSkill(WOMSkills.SOLAR_PASSIVE))
										{
											playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().setDataSync(WOMSkillDataKeys.HEAT_LEVEL.get(), 100f, (ServerPlayer) playerPatch.getOriginal());
										}
									}
								}
							}
						}, AnimationEvent.Side.SERVER),
						AnimationEvent.TimeStampedEvent.create(1.05f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.0F, -2.0F), Armatures.BIPED.legL, 4D, 3F));

		IMPERATRICE_SWORD_CROUCH_LIGHT = new AttackAnimation(0.02f, 0.0f, 0.7f, 0.8f, 1.75f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/crouch_light", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) ->
				{
					float baseSpd = 4f;
					if (livingEntityPatch instanceof PlayerPatch<?> playerPatch)
					{
						if (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && (playerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get())))
						{
							return baseSpd * 1.1f;
						}

					}
					return baseSpd;
				})				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);

		IMPERATRICE_SWORD_FTILT = new BasicAttackAnimation(0.1f, 0.0f, 0.01f, 0.03f, 0.27f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/forward_tilt", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_M.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f))
				.addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 10)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);
		IMPERATRICE_SWORD_CERCLE_DE_FEU = new AttackAnimation(0.1f, 0.0f, 0.12f, 0.15f, 2.75f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/forward_tilt_2", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING2.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_L.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f))
				.addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 10)
				.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);

		IMPERATRICE_SWORD_RTILT = new AttackAnimation(0.02f, "battle_style/legendary/imperatrice_lumiere/sword/right_tilt", biped,
				new AttackAnimation.Phase(0f, 0f, 0.15f, 0.35f, 3.75f, 3.75f, biped.legR, LumiereColliders.RTILT_LEG),
				new AttackAnimation.Phase(0f, 0f, 0.15f, 0.35f, 3.75f, 3.75f, biped.thighR, LumiereColliders.RTILT_THIGH))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(4))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_KICK_IMPACT_M.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);

		IMPERATRICE_SWORD_LTILT = new AttackAnimation(0.02f, "battle_style/legendary/imperatrice_lumiere/sword/left_tilt", biped,
				new AttackAnimation.Phase(0f, 0f, 0.05f, 0.12f, 3.75f, 3.75f, biped.handL, ColliderPreset.FIST))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(4))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_PUNCH_IMPACT_M.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);

		IMPERATRICE_SWORD_BTILT = new AttackAnimation(0.1f, 0.0f, 0.1f, 0.2f, 0.75f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/back_tilt", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f))
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addState(EntityState.TURNING_LOCKED, true);

		IMPERATRICE_SWORD_DASH_ATTACK = new AttackAnimation(0.2f, "battle_style/legendary/imperatrice_lumiere/sword/dash_attack", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.2f, 0.4f, 0.8f, 0.8f, biped.rootJoint, LumiereColliders.DASH_LIGHT))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING3.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_M.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2f))
				.addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 8)
				.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addState(EntityState.TURNING_LOCKED, true)
				.addState(EntityState.LOCKON_ROTATE, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d, e) -> 1.5f)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true);

		IMPERATRICE_SWORD_NEUTRAL_AERIAL = new AttackAnimation(0.0f, "battle_style/legendary/imperatrice_lumiere/sword/neutral_aerial", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.1f, 0.15f, 0.4f, 0.5f, biped.toolR, null),
				new AttackAnimation.Phase(0.5f, 0.0f, 0.7f, 0.75f, 1.5f, 1.5f, biped.toolR, null))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2f))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4f), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING2.get(), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get(), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2f), 1)
				.addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
				.addState(EntityState.TURNING_LOCKED, true)
				.addState(EntityState.LOCKON_ROTATE, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d, e) -> 1.5f)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
				.addEvents(AnimationEvent.TimeStampedEvent.create(0.0f, ((livingEntityPatch, staticAnimation, objects) ->
				{
					livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 2, 0, false, false, false));
				}), AnimationEvent.Side.CLIENT))
				.addEvents(AnimationProperty.StaticAnimationProperty.EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) ->
				{
					if (!AnimationHelpers.isInAir(livingEntityPatch))
					{
						livingEntityPatch.playAnimationSynchronized(livingEntityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getLivingMotionModifier(livingEntityPatch, InteractionHand.MAIN_HAND).get(LivingMotions.IDLE).get(), 0);
					}
				}, AnimationEvent.Side.CLIENT));

		IMPERATRICE_SWORD_FORWARD_AERIAL = new AttackAnimation(0.2f, "battle_style/legendary/imperatrice_lumiere/sword/forward_aerial", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.2f, 0.25f, 0.35f, 0.35f, biped.toolR, null),
				new AttackAnimation.Phase(0.35f, 0.0f, 0.4f, 0.45f, 0.55f, 0.55f, biped.toolR, null),
				new AttackAnimation.Phase(0.55f, 0.0f, 0.6f, 0.65f, 1.5f, 1.5f, biped.toolR, null))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4f), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get(), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get(), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f), 2)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 2)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_SWING1.get(), 2)
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_S.get(), 2)
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1f), 2)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.55f))
				.addState(EntityState.TURNING_LOCKED, true)
				.addState(EntityState.LOCKON_ROTATE, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d, e) -> 1.5f)
				.addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true);
		IMPERATRICE_SWORD_BLAZE_STINGER = new AttackAnimation(0.05f, 0.0f, 0.3f, 0.4f, 3.4f, null, biped.toolR, "battle_style/legendary/imperatrice_lumiere/sword/chargeattack", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundRegistry.IMPERATRICE_THRUST_L.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundRegistry.IMPERATRICE_HIT_L.get())
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2f))
				.addProperty(AnimationProperty.ActionAnimationProperty.RESET_PLAYER_COMBO_COUNTER, true)
				.addState(EntityState.TURNING_LOCKED, true)
				.addState(EntityState.LOCKON_ROTATE, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
		IMPERATRICE_BLAZE_BLASTER = new AttackAnimation(0.2f, 0.0f, 0.3f, 0.4f, 0.9f, null, biped.handL, "battle_style/legendary/imperatrice_lumiere/sword/combat_arts/blaze_blaster", biped);

		IMPERATRICE_PROMINENCE_BLAZE = new AttackAnimation(0.2f, 0.0f, 0.3f, 0.4f, 0.9f, null, biped.handL, "battle_style/legendary/imperatrice_lumiere/sword/combat_arts/prominence_blaze", biped);
		//Trailblaze
		IMPERATRICE_TRAILBLAZE_FWD = new DodgeAnimation(0.1F, 0.8F, "battle_style/legendary/imperatrice_lumiere/sword/skills/trailblaze_fwd", 0.6F, 1.65F, biped)
				.addState(EntityState.LOCKON_ROTATE, true)
				.newTimePair(0.0F, 1F)
				.addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
				.addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
		IMPERATRICE_TRAILBLAZE_SPOT = new DodgeAnimation(0.1F, 0.5F, "battle_style/legendary/imperatrice_lumiere/sword/skills/trailblaze_spot", 0.6F, 1.65F, biped)
				.addState(EntityState.LOCKON_ROTATE, true)
				.newTimePair(0.0F, 0.7F)
				.addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
				.addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
		IMPERATRICE_TRAILBLAZE_BACK = new DodgeAnimation(0.1F, 1F, "battle_style/legendary/imperatrice_lumiere/sword/skills/trailblaze_back", 0.6F, 1.65F, biped)
				.addState(EntityState.LOCKON_ROTATE, true)
				.newTimePair(0.0F, 1F)
				.addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
				.addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
		IMPERATRICE_TRAILBLAZE_LEFT = new DodgeAnimation(0.1F, 1F, "battle_style/legendary/imperatrice_lumiere/sword/skills/trailblaze_left", 0.6F, 1.65F, biped)
				.addState(EntityState.LOCKON_ROTATE, true)
				.newTimePair(0.0F, 1F)
				.addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
				.addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
		IMPERATRICE_TRAILBLAZE_RIGHT = new DodgeAnimation(0.1F, 1F, "battle_style/legendary/imperatrice_lumiere/sword/skills/trailblaze_right", 0.6F, 1.65F, biped)
				.addState(EntityState.LOCKON_ROTATE, true)
				.newTimePair(0.0F, 1F)
				.addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
				.addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false);
		//Combat Arts
		//Hero Sword Style
		HERO_SWORD_IDLE = new StaticAnimation(true, "sword/hero_hold_sword", biped);
		HERO_SWORD_AUTO_1 = new BasicAttackAnimation(0.1f, 0.1f, 0.6f, 0.9f, 0.9f, null, biped.toolR, "sword/hero_sword_auto1", biped)
				.addEvents(AnimationEvent.TimeStampedEvent.create(0.75F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.toolR, 1.1D, 0.55F));
		HERO_SWORD_AUTO_2 = new BasicAttackAnimation(0.1f, 0.0f, 0.45f, 0.9f, 0.9f, null, biped.toolR, "sword/hero_sword_auto2", biped);
		HERO_SWORD_AUTO_3 = new BasicAttackAnimation(0.1f, 0.0f, 0.8f, 1.2f, 2.2f, null, biped.toolR, "sword/hero_sword_auto3", biped);
		HERO_SHIELD_AUTO_1 = new BasicAttackAnimation(0.1f, 0.0f, 0.45f, 0.9f, 0.9f, HeroSwordColliders.HERO_SHIELD, biped.toolL, "sword/hero_shield_auto1", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d, e)-> 1.3f);
		HERO_SHIELD_AUTO_2 = new BasicAttackAnimation(0.1f, 0.0f, 0.0f, 0.3f, 0.9f, HeroSwordColliders.HERO_SHIELD, biped.toolL, "sword/hero_shield_auto2", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d, e)-> 1.3f);
		HERO_SWORD_GUARD = new StaticAnimation(0.4f, true, "sword/hero_sword_block", biped);
		HERO_SWORD_GUARD_HIT = new GuardAnimation(0.1f, "sword/hero_sword_block_hit", biped);
		SLAMMING_HERO = new BasicAttackAnimation(0.1f, 0.0f, 1.2f, 1.6f, 4.2f, HeroSwordColliders.SLAMMING_HERO, biped.rootJoint, "sword/slamming_hero", biped)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 1.2f))
				.addEvents(AnimationEvent.TimeStampedEvent.create(1.2F, Animations.ReusableSources.FRACTURE_METEOR_STRIKE, AnimationEvent.Side.SERVER)
						.params(new Vec3f(0.0F, -0.2F, -1.8F), Armatures.BIPED.toolR, 0.3F));
		HERO_SHIELD_BLOCK = new StaticAnimation(true, "sword/hero_shield_block", biped);
		HERO_SHIELD_BLOCK_HIT = new GuardAnimation(0.1f, "sword/hero_shield_block_hit", biped);
		//Dancer Chakram Style
		SINGLE_CHAKRAM_IDLE = new StaticAnimation(true, "chakram/chakram_idle", biped);
		SINGLE_CHAKRAM_WALK = new MovementAnimation(true, "chakram/chakram_walk", biped);
		SINGLE_CHAKRAM_AUTO_1 = new BasicAttackAnimation(0.1f, 0.0f, 0.15f, 0.8f, 1.2f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_auto1", biped)
			.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
			.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);
		SINGLE_CHAKRAM_AUTO_2 = new BasicAttackAnimation(0.1f, 0.0f, 0.4f, 0.8f, 5.8f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_auto2", biped)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);
		SINGLE_CHAKRAM_AIR_SLASH = new AttackAnimation(0.1f, "chakram/chakram_airslash", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.0f, 0.3f, 0.3f, 0.3f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.3f, 0.0f, 0.0f, 0.6f, 0.6f, 0.6f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.6f, 0.0f, 0.0f, 0.9f, 0.9f, 0.9f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.9f, 0.0f, 0.0f, 1.1f, 1.1f, 1.1f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(1.1f, 0.0f, 0.0f, 1.3f, 1.3f, 1.3f, biped.toolR, ColliderPreset.DAGGER),
				new AttackAnimation.Phase(0.0f, 0.0f, 0.1f, 0.0f, 2.5f, 2.5f, biped.toolR, ColliderPreset.DAGGER)
		)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(ChakramBalancing.CHAKRAM_AERIAL_DAMAGE_BONUS))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2f), 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f), 2)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f), 4)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ChakramBalancing.CHAKRAM_AERIAL_STUN_TYPE, 5)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f), 5)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 1.3f));
		PRECISION_VERTICAL = new AttackAnimation(0.1f, "chakram/precision_vertical", biped,
			new AttackAnimation.Phase(0.0f, 0.1f, 2.7f, 3.4f, 3.5f, 3.5f, biped.rootJoint, ChakramColliders.PRECISION_VERTICAL),
			new AttackAnimation.Phase(3.5f, 0.1f, 3.5f, 3.9f, 3.9f, 3.9f, biped.rootJoint, ChakramColliders.PRECISION_VERTICAL))
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 1)
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(14f));
		SINGLE_CHAKRAM_DASH_ATTACK = new DashAttackAnimation(0.1f, 0.0f, 0.15f, 0.8f, 2.2f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_dash", biped)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);

		HOUSE_LUX_GS_EXCALIBUR_IDLE = new StaticAnimation(0.1f, true, "battle_style/legendary/house_lux/arms_master/gs_excalibur/idle", biped);

		//Test Combat Arts using existing animations

		BLAZE_WHEEL = new BasicAttackAnimation(0.1f, "battle_style/legendary/imperatrice_lumiere/sword/infernal_wheel", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.0f, 0.2f, 0.4f, 0.4f, biped.rootJoint, null),
				new AttackAnimation.Phase(0.4f, 0.0f, 0.5f, 0.8f, 1.3f, 1.6f, biped.toolR, null)
		)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(0.5f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a,b,c,d, e) -> 1.4f)
				.addEvents(
						AnimationEvent.TimeStampedEvent.create(0.1F, (entitypatch, animation, params) ->
						{
								Entity entity = entitypatch.getOriginal();
								entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.2F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.3F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.4F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.5F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.6F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.7F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.8F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(0.9F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(1.0F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(1.1F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT),
						AnimationEvent.TimeStampedEvent.create(1.2F, (entitypatch, animation, params) ->
						{
							Entity entity = entitypatch.getOriginal();
							entity.level().addParticle(EpicFightParticles.ENTITY_AFTER_IMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0, 0);
						}, AnimationEvent.Side.CLIENT)
				);
	}
}
