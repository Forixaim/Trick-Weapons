package net.forixaim.battle_arts.core_assets.skills;

import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum BattleArtsSkillSlots implements SkillSlot
{
	BATTLE_STYLE(BattleArtsSkillCategories.BATTLE_STYLE),
	COMBAT_ART(BattleArtsSkillCategories.COMBAT_ART),
	TAUNT(BattleArtsSkillCategories.TAUNT),
	BURST_ART(BattleArtsSkillCategories.BURST_ART),
	ULTIMATE_ART(BattleArtsSkillCategories.ULTIMATE_ART);
	SkillCategory category;
	int id;

	BattleArtsSkillSlots(SkillCategory BattleStyle)
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
