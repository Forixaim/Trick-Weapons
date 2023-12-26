package net.forixaim.epic_fight_battle_styles.core_assets.skills;

import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum EpicFightBattleStyleSkillSlots implements SkillSlot
{
	BATTLE_STYLE(EpicFightBattleStyleCategories.BATTLE_STYLE);
	EpicFightBattleStyleCategories category;
	int id;

	EpicFightBattleStyleSkillSlots(EpicFightBattleStyleCategories BattleStyle)
	{
		this.category = BattleStyle;
		this.id = SkillSlot.ENUM_MANAGER.assign(this);
	}

	@Override
	public SkillCategory category()
	{
		return this.category;
	}

	@Override
	public int universalOrdinal()
	{
		return this.id;
	}
}
