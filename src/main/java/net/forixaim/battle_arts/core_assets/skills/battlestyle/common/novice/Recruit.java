package net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice;

import net.forixaim.bs_api.battle_arts_skills.battle_style.BattleStyle;
import net.forixaim.bs_api.proficiencies.Proficiencies;

public class Recruit extends BattleStyle
{

	public Recruit(Builder<?> builder)
	{
		super(builder);
		proficiencySpecialization.add(Proficiencies.POLEARMS);
	}
}
