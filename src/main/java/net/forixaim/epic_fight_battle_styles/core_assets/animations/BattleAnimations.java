package net.forixaim.epic_fight_battle_styles.core_assets.animations;


import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.colliders.ChakramColliders;
import net.forixaim.epic_fight_battle_styles.core_assets.colliders.HeroSwordColliders;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.*;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.identity.MeteorSlamSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

/**
 * Chakram Animation Module,
 */
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BattleAnimations extends BattleStylesAnimation
{
	//Base Rapier Animations
	public static StaticAnimation RAPIER_IDLE;
	//Imperatrice Luminelle
	public static StaticAnimation IMPERATRICE_SWORD_EN_GARDE;
	public static StaticAnimation IMPERATRICE_SWORD_WALK;
	public static StaticAnimation IMPERATRICE_SWORD_RUN;
	public static StaticAnimation IMPERATRICE_SWORD_AUTO1;
	public static StaticAnimation IMPERATRICE_SWORD_AUTO2;
	public static StaticAnimation IMPERATRICE_SWORD_FLAME_DANCE;
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

	//Dancer Chakram
	public static StaticAnimation SINGLE_CHAKRAM_IDLE;
	public static StaticAnimation SINGLE_CHAKRAM_WALK;
	public static StaticAnimation SINGLE_CHAKRAM_RUN;
	//Combat Motions
	public static StaticAnimation SINGLE_CHAKRAM_AUTO_1;
	public static StaticAnimation SINGLE_CHAKRAM_AUTO_2;
	public static StaticAnimation SINGLE_CHAKRAM_AIR_SLASH;
	public static StaticAnimation SINGLE_CHAKRAM_DASH_ATTACK;
	//Innate Skills
	public static StaticAnimation PRECISION_VERTICAL;

	@Override
	protected void Build()
	{

		HumanoidArmature biped = Armatures.BIPED;
		//Rapier Animations
		RAPIER_IDLE = new StaticAnimation(true, "rapier/idle", biped);
		//Imperatrice Luminelle Style (All animations aren't affected by attack speed)
		IMPERATRICE_SWORD_EN_GARDE = new StaticAnimation(true, "sword/imperatrice_luminelle_idle", biped);
		IMPERATRICE_SWORD_WALK = new MovementAnimation(true, "sword/imperatrice_luminelle_walk", biped);
		IMPERATRICE_SWORD_RUN = new MovementAnimation(true, "sword/imperatrice_luminelle_run", biped);
		IMPERATRICE_SWORD_AUTO1 = new BasicAttackAnimation(0.1f, 0.0f, 0.5f, 0.9f, 1.3f, null, biped.toolR, "sword/imperatrice_luminelle_auto1", biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a,b,c,d) -> 1.5f);
		IMPERATRICE_SWORD_AUTO2 = new BasicAttackAnimation(0.1f, "sword/imperatrice_luminelle_auto2", biped,
			new AttackAnimation.Phase(0.0f, 0.0f, 0.8f, 0.9f, 1.0f, 1.0f, biped.toolR, null),
				new AttackAnimation.Phase(1.0f, 0.0f, 1.5f, 1.9f, 2.4f, 2.4f, biped.toolR, null))
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a,b,c,d) -> 1.5f);
		IMPERATRICE_SWORD_FLAME_DANCE = new BasicAttackAnimation(0.1f, "sword/imperatrice_luminelle_auto3", biped,
			new AttackAnimation.Phase(0.0f, 0.0f, 0.1f, 0.2f, 0.3f, 0.3f, biped.toolR, null),
				new AttackAnimation.Phase(0.2f, 0.0f, 0.25f, 0.3f, 0.4f, 0.4f, biped.toolR, null),
				new AttackAnimation.Phase(0.4f, 0.0f, 0.45f, 0.5f, 0.6f, 0.6f, biped.toolR, null),
				new AttackAnimation.Phase(0.6f, 0.0f, 0.65f, 0.7f, 0.8f, 0.8f, biped.toolR, null),
				new AttackAnimation.Phase(0.8f, 0.0f, 1f, 1.1f, 1.5f, 2.1f, biped.toolR, null))
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a,b,c,d) -> 1.5f);
		//Hero Sword Style
		HERO_SWORD_IDLE = new StaticAnimation(true, "sword/hero_hold_sword", biped);
		HERO_SWORD_AUTO_1 = new BasicAttackAnimation(0.1f, 0.1f, 0.6f, 0.9f, 0.9f, null, biped.toolR, "sword/hero_sword_auto1", biped)
				.addEvents(AnimationEvent.TimeStampedEvent.create(0.75F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.toolR, 1.1D, 0.55F));
		HERO_SWORD_AUTO_2 = new BasicAttackAnimation(0.1f, 0.0f, 0.45f, 0.9f, 0.9f, null, biped.toolR, "sword/hero_sword_auto2", biped);
		HERO_SWORD_AUTO_3 = new BasicAttackAnimation(0.1f, 0.0f, 0.8f, 1.2f, 2.2f, null, biped.toolR, "sword/hero_sword_auto3", biped);
		HERO_SHIELD_AUTO_1 = new BasicAttackAnimation(0.1f, 0.0f, 0.45f, 0.9f, 0.9f, HeroSwordColliders.HERO_SHIELD, biped.toolL, "sword/hero_shield_auto1", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d )-> 1.3f);
		HERO_SHIELD_AUTO_2 = new BasicAttackAnimation(0.1f, 0.0f, 0.0f, 0.3f, 0.9f, HeroSwordColliders.HERO_SHIELD, biped.toolL, "sword/hero_shield_auto2", biped)
				.addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (a, b, c, d )-> 1.3f);
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
			.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
			.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.9f));
		SINGLE_CHAKRAM_AUTO_2 = new BasicAttackAnimation(0.1f, 0.0f, 0.4f, 0.8f, 5.8f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_auto2", biped)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.8f));
		SINGLE_CHAKRAM_AIR_SLASH = new AttackAnimation(0.1f, "chakram/chakram_airslash", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.0f, 0.3f, 0.3f, 0.3f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.3f, 0.0f, 0.0f, 0.6f, 0.6f, 0.6f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.6f, 0.0f, 0.0f, 0.9f, 0.9f, 0.9f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.9f, 0.0f, 0.0f, 1.1f, 1.1f, 1.1f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(1.1f, 0.0f, 0.0f, 1.3f, 1.3f, 1.3f, biped.toolR, ColliderPreset.DAGGER),
				new AttackAnimation.Phase(0.0f, 0.0f, 0.1f, 0.0f, 2.5f, 2.5f, biped.toolR, ColliderPreset.DAGGER)
		)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 1.3f));
		PRECISION_VERTICAL = new AttackAnimation(0.1f, "chakram/precision_vertical", biped,
			new AttackAnimation.Phase(0.0f, 0.1f, 2.7f, 3.4f, 3.5f, 3.5f, biped.rootJoint, ChakramColliders.PRECISION_VERTICAL),
			new AttackAnimation.Phase(3.5f, 0.1f, 3.5f, 3.9f, 3.9f, 3.9f, biped.rootJoint, ChakramColliders.PRECISION_VERTICAL))
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f);
		SINGLE_CHAKRAM_DASH_ATTACK = new BasicAttackAnimation(0.1f, 0.0f, 0.15f, 0.8f, 1.2f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_dash", biped)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1f)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.9f));

	}
	public static class ReusableSources {
		/**
		 * Adds a camera shake for the player
		 * @param params[0] An integer on how long the shake is.
		 * @param params[1] A float on the shake strength.
		 * @param params[3] A float on the frequency of the shake.
		 */
		//TODO: Make a screenshake event.
//		public static final AnimationEvent.AnimationEventConsumer SHAKE_CAMERA = (entitypatch, animation, params) ->
//		{
//			if (params.length > 3)
//			{
//				throw(new ArgumentCountException(1, 1, "You fucking moron why did you more than 3 arguments for a camera shake?"));
//			}
//			CameraEngine.getInstance().shakeCamera((Integer) params[0], (float) params[1], params[2]);
//		};
	}
}
