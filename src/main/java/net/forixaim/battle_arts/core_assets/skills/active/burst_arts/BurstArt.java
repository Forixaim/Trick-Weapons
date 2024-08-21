package net.forixaim.battle_arts.core_assets.skills.active.burst_arts;

import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillCategories;
import net.forixaim.battle_arts.core_assets.skills.active.ActiveSkill;
import yesman.epicfight.skill.Skill;

/**
 * This class isn't supposed to be used. It's mainly here for organization
 */

public abstract class BurstArt extends ActiveSkill
{

	public static Builder<BurstArt> createBurstArt()
	{
		return (new Builder<BurstArt>().setCategory(BattleArtsSkillCategories.BURST_ART).setResource(Resource.COOLDOWN));
	}

	public BurstArt(Builder<? extends Skill> builder) {
		super(builder);
	}

}
