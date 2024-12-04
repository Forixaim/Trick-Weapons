package net.forixaim.battle_arts.core_assets.animations.battle_style.novice.squire;

import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class SquireSwordAnimations
{
	public static StaticAnimation SQUIRE_SWORD_IDLE;
	public static StaticAnimation SQUIRE_SWORD_WALK;
	public static StaticAnimation SQUIRE_SWORD_RUN;
	public static StaticAnimation SQUIRE_SWORD_GUARD;
	public static StaticAnimation SQUIRE_SWORD_GUARD_HIT_1;
	public static StaticAnimation SQUIRE_SWORD_GUARD_HIT_2;
	public static StaticAnimation SQUIRE_SWORD_CROUCH;
	public static StaticAnimation SQUIRE_SWORD_CROUCH_WALK;
	public static StaticAnimation SQUIRE_SWORD_AUTO_1;
	public static StaticAnimation SQUIRE_SWORD_AUTO_2;
	public static StaticAnimation SQUIRE_SWORD_AUTO_3;
	public static StaticAnimation SQUIRE_SWORD_DASH_ATTACK;
	public static StaticAnimation SQUIRE_SWORD_HOP_ATTACK;

	public static void Build()
	{
		HumanoidArmature biped = Armatures.BIPED;
		SQUIRE_SWORD_IDLE = new StaticAnimation(true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "idle"), biped);
		SQUIRE_SWORD_WALK = new MovementAnimation(0.3f, true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "walk"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 2f);
		SQUIRE_SWORD_RUN = new MovementAnimation(true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "run"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1.7f);
		SQUIRE_SWORD_CROUCH = new StaticAnimation(true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "crouch"), biped)
				.addState(EntityState.MOVEMENT_LOCKED, true);
		SQUIRE_SWORD_CROUCH_WALK = new MovementAnimation(0.2f, true, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "crouch_walk"), biped)
				.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, v, v1, v2) -> 1f);
		SQUIRE_SWORD_AUTO_1 = new BasicAttackAnimation(0f, 0f, 0.35f, 0.5f, 0.7f, null, biped.toolR, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "auto1"), biped);
		SQUIRE_SWORD_AUTO_2 = new BasicAttackAnimation(0f, 0f, 0.45f, 0.55f, 0.8f, null, biped.toolR, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "auto2"), biped);
		SQUIRE_SWORD_AUTO_3 = new BasicAttackAnimation(0f, 0f, 0.45f, 0.55f, 1.0f, null, biped.toolR, SquireAnimations.squireAnimationPath(CapabilityItem.WeaponCategories.SWORD, "auto3"), biped);
	}

}
