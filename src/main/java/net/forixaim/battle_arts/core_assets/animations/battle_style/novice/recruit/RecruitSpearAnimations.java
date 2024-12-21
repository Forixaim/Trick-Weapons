package net.forixaim.battle_arts.core_assets.animations.battle_style.novice.recruit;

import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class RecruitSpearAnimations
{
	public static StaticAnimation RECRUIT_SPEAR_IDLE;
	public static StaticAnimation RECRUIT_SPEAR_WALK;
	public static StaticAnimation RECRUIT_SPEAR_RUN;
	public static StaticAnimation RECRUIT_SPEAR_CROUCH;
	public static StaticAnimation RECRUIT_SPEAR_STANDING_ATTACK;
	public static StaticAnimation RECRUIT_SPEAR_STANDING_ATTACK_2;
	public static StaticAnimation RECRUIT_SPEAR_DASH_ATTACK;
	public static StaticAnimation RECRUIT_SPEAR_AERIAL_POKE;


	public static void Build()
	{
		HumanoidArmature biped = Armatures.BIPED;
		RECRUIT_SPEAR_IDLE = new StaticAnimation(true, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "idle"), biped);
		RECRUIT_SPEAR_WALK = new MovementAnimation(true, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "walk"), biped);
		RECRUIT_SPEAR_RUN = new MovementAnimation(true, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "run"), biped);
		RECRUIT_SPEAR_CROUCH = new StaticAnimation(true, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "crouch"), biped);
		RECRUIT_SPEAR_STANDING_ATTACK = new BasicAttackAnimation(0.2f, 0.0f, 0.35f, 0.5f, 0.75f, null, biped.toolR, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "standing_attack"), biped);
		RECRUIT_SPEAR_STANDING_ATTACK_2 = new BasicAttackAnimation(0.0f, 0.0f, 0.55f, 0.8f, 1.5f, null, biped.toolR, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "standing_attack2"), biped);
		RECRUIT_SPEAR_DASH_ATTACK = new DashAttackAnimation(0.0f, 0.0f, 0.7f, 0.8f, 1.7f, null, biped.toolR, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "dash_attack"), biped);
		RECRUIT_SPEAR_AERIAL_POKE = new AirSlashAnimation(0.0f, 0.0f, 0.7f, 0.8f, 1.7f, false,null, biped.toolR, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "aerial_poke"), biped);

	}
}
