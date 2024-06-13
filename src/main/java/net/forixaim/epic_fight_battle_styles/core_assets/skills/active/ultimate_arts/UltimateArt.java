package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ultimate_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import yesman.epicfight.skill.Skill;

/**
 * This class isn't supposed to be used. It's mainly here for organization
 */

public abstract class UltimateArt extends ActiveSkill
{

	public static Builder createCombatArt()
	{
		return (new Builder().setCategory(EpicFightBattleStyleSkillCategories.ULTIMATE_ART).setResource(Resource.COOLDOWN));
	}

	public UltimateArt(Builder<? extends Skill> builder) {
		super(builder);
	}

}
