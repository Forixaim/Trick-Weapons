package net.forixaim.battle_arts.core_assets.skills.weaponinnate;

import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class IronFortress extends WeaponInnateSkill
{
	private static final UUID ID = UUID.fromString("7d0148e7-f1ed-4715-8123-0e69b4d49c40");
	public IronFortress(Builder<? extends Skill> builder)
	{

		super(builder);
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args) {
		super.executeOnServer(executor, args);
		this.setDurationSynchronize(executor, this.getMaxDuration());
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, ID, (event) ->
		{
			if (container.getRemainDuration() > 0)
			{
				event.setResult(AttackResult.ResultType.BLOCKED);
			}
		});
	}

	@Override
	public void onRemoved(SkillContainer container) {
		super.onRemoved(container);

		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, ID);
	}
}
