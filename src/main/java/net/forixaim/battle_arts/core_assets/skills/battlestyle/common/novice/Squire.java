package net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice;

import net.forixaim.bs_api.battle_arts_skills.battle_style.BattleStyle;
import net.forixaim.bs_api.proficiencies.Proficiencies;

public class Squire extends BattleStyle
{
	public Squire(Builder<?> builder)
	{
		super(builder);
		proficiencySpecialization.add(Proficiencies.SWORDS);
		proficiencySpecialization.add(Proficiencies.BOWS);
		proficiencySpecialization.add(Proficiencies.DAGGERS);
		proficiencySpecialization.add(Proficiencies.RIDING);
	}




}
