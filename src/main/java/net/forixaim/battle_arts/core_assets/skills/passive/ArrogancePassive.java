package net.forixaim.battle_arts.core_assets.skills.passive;

import com.mojang.blaze3d.vertex.PoseStack;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.BerserkerSkill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class ArrogancePassive extends PassiveSkill
{
	private static final UUID EVENT_UUID = UUID.fromString("ab53525f-0b2f-447a-87d0-7d39c5a56086");

	public ArrogancePassive(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ANIMATION_BEGIN_EVENT, EVENT_UUID, event ->
		{
			if (!event.getPlayerPatch().isLogicalClient())
				container.getDataManager().setDataSync(BattleArtsDataKeys.ANIM_ID.get(), false, (ServerPlayer) event.getPlayerPatch().getOriginal());
		});
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID, event ->
		{
			if (!container.getDataManager().getDataValue(BattleArtsDataKeys.ANIM_ID.get()))
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.ANIM_ID.get(), true, event.getPlayerPatch().getOriginal());
				if (container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) < 10)
					container.getDataManager().setDataSync(BattleArtsDataKeys.ARROGANCE_STACK.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) + 1, event.getPlayerPatch().getOriginal());
			}
		});
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID, event ->
		{
			int stack = (int) (container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) + 0.95f);
			float attackSpdBonus = 0.05f * stack;
			event.setAttackSpeed(event.getAttackSpeed() * (1 + attackSpdBonus));
		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, event->
		{
			int stack = (int) (container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) + 0.95f);
			float attackSpdBonus = 0.01f * stack;
			if (event.isParried())
			{
				if (container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) < 10)
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.ARROGANCE_STACK.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) + 1, event.getPlayerPatch().getOriginal());
				}
			}
			else if (event.getResult() == AttackResult.ResultType.BLOCKED)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.ARROGANCE_STACK.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) - 2, event.getPlayerPatch().getOriginal());
				if (container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) < 0)
					container.getDataManager().setDataSync(BattleArtsDataKeys.ARROGANCE_STACK.get(), 0f, event.getPlayerPatch().getOriginal());

			}
			else if (event.getResult() == AttackResult.ResultType.SUCCESS)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.ARROGANCE_STACK.get(), 0f, event.getPlayerPatch().getOriginal());
			}
			event.setAmount(event.getAmount() * (1 + attackSpdBonus));
		});
	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		super.onRemoved(container);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID);
	}

	@Override
	public boolean shouldDraw(SkillContainer container)
	{
		return true;
	}

	@Override
	public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y)
	{
		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.translate(0, (float)gui.getSlidingProgression(), 0);
		guiGraphics.blit(getSkillTexture(), (int)x-4, (int)y-4, 36, 36, 0, 0, 1, 1, 1, 1);
		Float Heat = container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get());
		String Heat_Level = String.format("%.0f", Heat);
		guiGraphics.drawString(gui.font, Heat_Level, x + 4, y + 6, 16777215, true);
		poseStack.popPose();
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		super.updateContainer(container);
		if (!container.getExecuter().isLogicalClient())
		{
			if (container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) > 0)
				container.getDataManager().setDataSync(BattleArtsDataKeys.ARROGANCE_STACK.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.ARROGANCE_STACK.get()) - 0.01f, (ServerPlayer) container.getExecuter().getOriginal());

		}
	}
}
