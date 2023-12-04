package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle;

import yesman.epicfight.skill.Skill;

import java.util.UUID;

public class Hero extends BattleStyles
{
	private static final UUID EVENT_UUID = UUID.fromString("3421b224-d2a4-482b-ad36-8a19b4aa0d25");
	public Hero(Builder<? extends Skill> builder) {
		super(builder);
	}
}
