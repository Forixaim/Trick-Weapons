package net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponpassives;

import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class JoyeusePassive extends Skill
{
	private static final UUID EVENT_UUID = UUID.fromString("2b3a8bea-cf7e-4710-ba98-68dc0f9bce30");
	private final AttackAnimationProvider swordDraw;
	public JoyeusePassive(Builder<? extends Skill> builder)
	{
		super(builder);
		swordDraw = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_JOYEUSE_DRAW;
	}

	@Override
	public void onInitiate(SkillContainer container)
	{

	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.SKILL_EXECUTE_EVENT, EVENT_UUID);
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		executor.playAnimationSynchronized(swordDraw.get(), 0);
	}

	@Override
	public boolean shouldDeactivateAutomatically(PlayerPatch<?> executor)
	{
		return true;
	}
}
