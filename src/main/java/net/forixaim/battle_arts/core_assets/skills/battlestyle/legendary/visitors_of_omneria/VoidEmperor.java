package net.forixaim.battle_arts.core_assets.skills.battlestyle.legendary.visitors_of_omneria;

import net.forixaim.battle_arts.core_assets.skills.battlestyle.BattleStyle;
import net.minecraft.world.damagesource.DamageTypes;
import yesman.epicfight.skill.Skill;

public class VoidEmperor extends BattleStyle
{
	public VoidEmperor(Builder<? extends Skill> builder)
	{
		super(builder);
		immuneDamages.add(DamageTypes.FELL_OUT_OF_WORLD);
	}
}
