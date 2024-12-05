package net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice;

import net.forixaim.battle_arts.core_assets.animations.battle_style.novice.squire.SquireSwordAnimations;
import net.forixaim.bs_api.battle_arts_skills.battle_style.BattleStyle;
import net.forixaim.bs_api.proficiencies.Proficiencies;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;

public class Squire extends BattleStyle
{
	//Linked Skills
	public static Skill HEAVY_BLOW;

	public Squire(Builder<?> builder)
	{
		super(builder);
		proficiencySpecialization.add(Proficiencies.SWORDS);
		proficiencySpecialization.add(Proficiencies.BOWS);
		proficiencySpecialization.add(Proficiencies.DAGGERS);
		proficiencySpecialization.add(Proficiencies.RIDING);
	}



	public static void RegisterInnates(SkillBuildEvent.ModRegistryWorker worker)
	{
		HEAVY_BLOW = worker.build("squire_heavy_blow", SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations( () -> (AttackAnimation) SquireSwordAnimations.SQUIRE_SWORD_HEAVY_BLOW))
				.newProperty();
	}
}
