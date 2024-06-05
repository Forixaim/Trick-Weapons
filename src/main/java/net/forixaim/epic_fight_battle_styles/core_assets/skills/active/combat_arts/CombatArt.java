package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import yesman.epicfight.skill.Skill;

/**
 * This class isn't supposed to be used. It's mainly here for organization
 */

public abstract class CombatArt extends ActiveSkill
{

	public static Builder createCombatArt()
	{
		return (new Builder().setCategory(EpicFightBattleStyleSkillCategories.COMBAT_ART).setResource(Resource.COOLDOWN));
	}

	public CombatArt(Builder<? extends Skill> builder) {
		super(builder);
	}

}
