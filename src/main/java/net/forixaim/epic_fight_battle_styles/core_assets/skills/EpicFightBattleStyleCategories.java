package net.forixaim.epic_fight_battle_styles.core_assets.skills;

import yesman.epicfight.skill.SkillCategory;

public enum EpicFightBattleStyleCategories implements SkillCategory
{
	BATTLE_STYLE(true, true, true);

	boolean Save;
	boolean Sync;
	boolean Modifiable;
	int ID;

	EpicFightBattleStyleCategories(boolean ShouldSave, boolean ShouldSync, boolean Modifiable)
	{
		this.Modifiable = Modifiable;
		this.Save = ShouldSave;
		this.Sync = ShouldSync;
		this.ID = SkillCategory.ENUM_MANAGER.assign(this);
	}

	@Override
	public boolean shouldSave()
	{
		return this.Save;
	}

	@Override
	public boolean shouldSynchronize()
	{
		return this.Sync;
	}

	@Override
	public boolean learnable()
	{
		return this.Modifiable;
	}
	@Override
	public int universalOrdinal()
	{
		return this.ID;
	}
}
