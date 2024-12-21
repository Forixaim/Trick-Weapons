package net.forixaim.battle_arts.core_assets.animations.battle_style.novice.squire;

import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.StunType;

public class SquireSwordAnimations
{
	public static StaticAnimation SQUIRE_SWORD_IDLE;
	public static StaticAnimation SQUIRE_SWORD_WALK;
	public static StaticAnimation SQUIRE_SWORD_RUN;
	public static StaticAnimation SQUIRE_SWORD_GUARD;
	public static StaticAnimation SQUIRE_SWORD_GUARD_HIT;
	public static StaticAnimation SQUIRE_SWORD_GUARD_PARRY_1;
	public static StaticAnimation SQUIRE_SWORD_GUARD_PARRY_2;
	public static StaticAnimation SQUIRE_SWORD_CROUCH;
	public static StaticAnimation SQUIRE_SWORD_CROUCH_WALK;
	public static StaticAnimation SQUIRE_SWORD_AUTO_1;
	public static StaticAnimation SQUIRE_SWORD_AUTO_2;
	public static StaticAnimation SQUIRE_SWORD_AUTO_3;
	public static StaticAnimation SQUIRE_SWORD_DASH_ATTACK;
	public static StaticAnimation SQUIRE_SWORD_HOP_ATTACK;

	public static StaticAnimation SQUIRE_SWORD_HEAVY_BLOW;

	public static void Build()
	{
		HumanoidArmature biped = Armatures.BIPED;
		SQUIRE_SWORD_IDLE = new StaticAnimation(true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "idle"), biped);
		SQUIRE_SWORD_WALK = new MovementAnimation(0.3f, true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "walk"), biped);
		SQUIRE_SWORD_RUN = new MovementAnimation(true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "run"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.7f);
		SQUIRE_SWORD_CROUCH = new StaticAnimation(true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "crouch"), biped)
				.addState(EntityState.MOVEMENT_LOCKED, true);
		SQUIRE_SWORD_CROUCH_WALK = new MovementAnimation(0.2f, true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "crouch_walk"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1f);
		SQUIRE_SWORD_AUTO_1 = new BasicAttackAnimation(0f, 0f, 0.35f, 0.5f, 0.7f, null, biped.toolR, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "auto1"), biped);
		SQUIRE_SWORD_AUTO_2 = new BasicAttackAnimation(0f, 0f, 0.45f, 0.55f, 0.8f, null, biped.toolR, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "auto2"), biped);
		SQUIRE_SWORD_AUTO_3 = new BasicAttackAnimation(0f, 0f, 0.45f, 0.55f, 1.0f, null, biped.toolR, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "auto3"), biped);
		SQUIRE_SWORD_DASH_ATTACK = new DashAttackAnimation(0.2f, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "dash_attack"), biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.2f, 0.3f, 0.4f, 0.5f, biped.toolR, null)
						.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f)),
				new AttackAnimation.Phase(0.5f, 0.0f, 0.9f, 1.0f, 1.5f, 2.0f, biped.toolR, null)
						.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
						.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7f))
						.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2f)));
		SQUIRE_SWORD_HOP_ATTACK = new AirSlashAnimation(0f, 0f, 0.5f, 0.65f, 2f, false, null, biped.toolR, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "hop_attack"), biped)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false);
		SQUIRE_SWORD_HEAVY_BLOW = new AttackAnimation(0f, 0f, 1.6f, 1.8f, 3f, ColliderPreset.DUAL_SWORD_AIR_SLASH, biped.rootJoint, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "heavy_blow"), biped)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.1f)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2f))
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true);
		SQUIRE_SWORD_GUARD = new StaticAnimation(0.1f, true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "guard"), biped);
		SQUIRE_SWORD_GUARD_HIT = new GuardAnimation(0.1f, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "guard_hit"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.5f);
		SQUIRE_SWORD_GUARD_PARRY_1 = new GuardAnimation(0.1f, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "guard_parry_1"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.5f);
		SQUIRE_SWORD_GUARD_PARRY_2 = new GuardAnimation(0.1f, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "guard_parry_2"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.5f);
	}
}
