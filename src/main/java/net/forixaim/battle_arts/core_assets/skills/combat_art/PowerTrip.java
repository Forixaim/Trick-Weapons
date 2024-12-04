package net.forixaim.battle_arts.core_assets.skills.combat_art;

import net.forixaim.bs_api.battle_arts_skills.active.combat_arts.CombatArt;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class PowerTrip extends CombatArt
{

	public PowerTrip(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		super.executeOnServer(executor, args);
		AnimationProvider<?> attack = executor.getHoldingItemCapability(InteractionHand.MAIN_HAND).getAutoAttckMotion(executor).get(0);
	}
}
