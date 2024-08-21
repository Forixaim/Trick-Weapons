package net.forixaim.battle_arts.core_assets.skills.battlestyle.legendary.visitors_of_omneria;

import net.forixaim.battle_arts.core_assets.skills.battlestyle.BattleStyle;
import net.minecraft.world.damagesource.DamageTypes;
import yesman.epicfight.skill.Skill;

public class AquaticMasamune extends BattleStyle
{
	public AquaticMasamune(Builder<? extends Skill> builder)
	{
		super(builder);
		immuneDamages.add(DamageTypes.DROWN);
	}
}
