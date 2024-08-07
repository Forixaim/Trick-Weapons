package net.forixaim.epic_fight_battle_styles.core_assets.skills;

import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum EpicFightBattleStyleSkillSlots implements SkillSlot
{
	BATTLE_STYLE(EpicFightBattleStyleSkillCategories.BATTLE_STYLE),
	COMBAT_ART(EpicFightBattleStyleSkillCategories.COMBAT_ART),
	TAUNT(EpicFightBattleStyleSkillCategories.TAUNT),
	BURST_ART(EpicFightBattleStyleSkillCategories.BURST_ART),
	ULTIMATE_ART(EpicFightBattleStyleSkillCategories.ULTIMATE_ART);
	SkillCategory category;
	int id;

	EpicFightBattleStyleSkillSlots(SkillCategory BattleStyle)
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
