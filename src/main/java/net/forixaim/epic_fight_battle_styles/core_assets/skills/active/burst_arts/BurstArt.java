package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import yesman.epicfight.skill.Skill;

/**
 * This class isn't supposed to be used. It's mainly here for organization
 */

public abstract class BurstArt extends ActiveSkill
{

	public static Builder<BurstArt> createBurstArt()
	{
		return (new Builder<BurstArt>().setCategory(EpicFightBattleStyleSkillCategories.BURST_ART).setResource(Resource.COOLDOWN));
	}

	public BurstArt(Builder<? extends Skill> builder) {
		super(builder);
	}

}
