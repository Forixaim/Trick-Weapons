package net.forixaim.battle_arts.core_assets.skills.active.combat_arts;

import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillCategories;
import net.forixaim.battle_arts.core_assets.skills.active.ActiveSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;

/**
 * This class isn't supposed to be used. It's mainly here for organization
 */

public abstract class CombatArt extends ActiveSkill
{

	public static Builder<CombatArt> createCombatArt()
	{
		return (new Builder<CombatArt>().setCategory(BattleArtsSkillCategories.COMBAT_ART).setResource(Resource.COOLDOWN));
	}

	public CombatArt(Builder<? extends Skill> builder) {
		super(builder);
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);

	}

}
