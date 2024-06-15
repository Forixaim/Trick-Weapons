package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.client.KeyBinds;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import net.minecraft.client.KeyMapping;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;

/**
 * This class isn't supposed to be used. It's mainly here for organization
 */

public abstract class CombatArt extends ActiveSkill
{

	public static Builder<CombatArt> createCombatArt()
	{
		return (new Builder<CombatArt>().setCategory(EpicFightBattleStyleSkillCategories.COMBAT_ART).setResource(Resource.COOLDOWN));
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
