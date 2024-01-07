package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.unique.wom;

import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import yesman.epicfight.skill.Skill;

import java.util.UUID;

/**
 * An extension of the Hero battle style that allows the wielding of Herrscher and Gesetz
 * Featuring a more defensive attribute modifier but less damage and slower.
 */
public class Atlantean extends BattleStyle
{

	private static final UUID EVENT_UUID = UUID.fromString("eefa564d-22d0-47eb-ab73-4cdafa7bee89");
	public Atlantean(Builder<? extends Skill> builder)
	{
		super(builder);
	}
}
