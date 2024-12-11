package net.forixaim.battle_arts.core_assets.animations.battle_style.novice.recruit;

import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class RecruitSpearAnimations
{
	public static StaticAnimation RECRUIT_SPEAR_IDLE;
	public static StaticAnimation RECRUIT_SPEAR_WALK;
	public static StaticAnimation RECRUIT_SPEAR_RUN;


	public static void Build()
	{
		HumanoidArmature biped = Armatures.BIPED;
		RECRUIT_SPEAR_IDLE = new StaticAnimation(true, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "idle"), biped);
		RECRUIT_SPEAR_WALK = new MovementAnimation(true, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "walk"), biped);
		RECRUIT_SPEAR_RUN = new MovementAnimation(true, RecruitAnimations.recruitAnimationPath(CapabilityItem.WeaponCategories.SPEAR, "run"), biped);
	}
}
