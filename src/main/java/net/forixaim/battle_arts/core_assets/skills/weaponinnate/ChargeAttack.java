package net.forixaim.battle_arts.core_assets.skills.weaponinnate;

import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

public class ChargeAttack extends WeaponInnateSkill
{
	public static Builder createChargeAttack()
	{
		return (new Builder().setCategory(SkillCategories.WEAPON_INNATE).setResource(Resource.STAMINA));
	}

	public ChargeAttack(Builder<? extends Skill> builder) {
		super(builder);
	}
}
