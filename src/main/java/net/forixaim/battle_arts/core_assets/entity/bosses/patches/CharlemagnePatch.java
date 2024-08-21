package net.forixaim.battle_arts.core_assets.entity.bosses.patches;

import net.forixaim.battle_arts.core_assets.entity.bosses.Charlemagne;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

import javax.sound.midi.Patch;

public class CharlemagnePatch extends HumanoidMobPatch<Charlemagne>
{
	public CharlemagnePatch()
	{
		super(Faction.NEUTRAL);

	}

	@Override
	public void initAnimator(Animator animator)
	{

	}

	@Override
	public void updateMotion(boolean b)
	{

	}
}
