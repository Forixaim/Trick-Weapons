package net.forixaim.epic_fight_battle_styles.core_assets.skills.basic_attack;

import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.BasicAttackEvent;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;

public class ImperatriceAttacks extends BasicAttack
{
	public static Skill.Builder<ImperatriceAttacks> createImperatriceAttackSet() {
		return (new Builder<ImperatriceAttacks>()).setCategory(SkillCategories.BASIC_ATTACK).setActivateType(ActivateType.ONE_SHOT).setResource(Resource.NONE);
	}

	private static final AttackAnimationProvider CROUCH_LIGHT = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_CROUCH_LIGHT;
	public ImperatriceAttacks(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	public static void setComboCounterWithEvent(ComboCounterHandleEvent.Causal reason, ServerPlayerPatch playerpatch, SkillContainer container, StaticAnimation causalAnimation, int value) {
		int prevValue = container.getDataManager().getDataValue(SkillDataKeys.COMBO_COUNTER.get());
		ComboCounterHandleEvent comboResetEvent = new ComboCounterHandleEvent(reason, playerpatch, causalAnimation, prevValue, value);
		container.getExecuter().getEventListener().triggerEvents(PlayerEventListener.EventType.COMBO_COUNTER_HANDLE_EVENT, comboResetEvent);
		container.getDataManager().setData(EFBSDataKeys.BLAZE_COMBO.get(), comboResetEvent.getNextValue());
	}


	@Override
	public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args)
	{
		if (executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(executer) == ImperatriceLumiereStyles.IMPERATRICE_SWORD)
		{
			if (!executer.getOriginal().isShiftKeyDown())
			{
				super.executeOnServer(executer, args);
			}
			SkillConsumeEvent event = new SkillConsumeEvent(executer, this, this.resource);
			executer.getEventListener().triggerEvents(PlayerEventListener.EventType.SKILL_CONSUME_EVENT, event);
			SkillDataManager dataManager = executer.getSkill(this).getDataManager();
			int comboCounter = dataManager.getDataValue(EFBSDataKeys.BLAZE_COMBO.get());

			if (!event.isCanceled()) {
				event.getResourceType().consumer.consume(this, executer, event.getAmount());
			}
			if (executer.getEventListener().triggerEvents(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, new BasicAttackEvent(executer)))
			{
				return;
			}



			if (executer.getOriginal().isShiftKeyDown())
			{
				setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ACTION_ANIMATION_RESET, executer, executer.getSkill(this), CROUCH_LIGHT.get(), comboCounter);
				executer.playAnimationSynchronized(CROUCH_LIGHT.get(), 0.0f);
				executer.updateEntityState();
			}

		}
		else
		{
			super.executeOnServer(executer, args);
		}
	}
}
