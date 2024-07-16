package net.forixaim.epic_fight_battle_styles.core_assets.skills.basic_attack;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import io.netty.buffer.Unpooled;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.BasicAttackEvent;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;

import java.util.List;

/**
 * This class replaces the basic attack motions when created.
 */
public class ImperatriceAttacks extends BasicAttack
{
	private static final List<AnimationProvider<?>> IMPERATRICE_SWORD_JAB_SET = Lists.newArrayList(
			() -> BattleAnimations.IMPERATRICE_SWORD_JAB1,
			() -> BattleAnimations.IMPERATRICE_SWORD_JAB2,
			() -> BattleAnimations.IMPERATRICE_SWORD_JAB3,
			() -> BattleAnimations.IMPERATRICE_SWORD_JAB4,
			() -> BattleAnimations.IMPERATRICE_SWORD_JAB5
	);

	public static Skill.Builder<ImperatriceAttacks> createImperatriceAttackSet()
	{
		return (new Builder<ImperatriceAttacks>()).setCategory(SkillCategories.BASIC_ATTACK).setActivateType(ActivateType.ONE_SHOT).setResource(Resource.NONE);
	}
	private static final AttackAnimationProvider DASH_ATTACK = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_DASH_ATTACK;
	private static final AttackAnimationProvider CROUCH_LIGHT = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_CROUCH_LIGHT;
	private static final AttackAnimationProvider FTILT = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_FTILT;
	private static final AttackAnimationProvider RTILT = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_RTILT;
	private static final AttackAnimationProvider LTILT = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_LTILT;
	private static final AttackAnimationProvider BTILT = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_BTILT;
	public ImperatriceAttacks(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	public static void setComboCounterWithEvent(ComboCounterHandleEvent.Causal reason, ServerPlayerPatch playerpatch, SkillContainer container, StaticAnimation causalAnimation, int value)
	{
		int prevValue = container.getDataManager().getDataValue(SkillDataKeys.COMBO_COUNTER.get());
		ComboCounterHandleEvent comboResetEvent = new ComboCounterHandleEvent(reason, playerpatch, causalAnimation, prevValue, value);
		container.getExecuter().getEventListener().triggerEvents(PlayerEventListener.EventType.COMBO_COUNTER_HANDLE_EVENT, comboResetEvent);
		container.getDataManager().setData(EFBSDataKeys.BLAZE_COMBO.get(), comboResetEvent.getNextValue());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public FriendlyByteBuf gatherArguments(LocalPlayerPatch executer, ControllEngine controllEngine)
	{
		Input input = executer.getOriginal().input;
		float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(executer.getOriginal()), 0.0F, 1.0F);
		input.tick(false, pulse);

		int forward = controllEngine.isKeyDown(Minecraft.getInstance().options.keyUp) ? 1 : 0;
		int backward = controllEngine.isKeyDown(Minecraft.getInstance().options.keyDown) ? -1 : 0;
		int left = controllEngine.isKeyDown(Minecraft.getInstance().options.keyLeft) ? 1 : 0;
		int right = controllEngine.isKeyDown(Minecraft.getInstance().options.keyRight) ? -1 : 0;

		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeInt(forward);
		buf.writeInt(backward);
		buf.writeInt(left);
		buf.writeInt(right);

		return buf;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public Object getExecutionPacket(LocalPlayerPatch executer, FriendlyByteBuf args)
	{
		int forward = args.readInt();
		int backward = args.readInt();
		int left = args.readInt();
		int right = args.readInt();
		int vertic = forward + backward;
		int horizon = left + right;

		CPExecuteSkill packet = new CPExecuteSkill(executer.getSkill(this).getSlotId());
		packet.getBuffer().writeInt(Integer.compare(vertic, 0));
		packet.getBuffer().writeInt(Integer.compare(horizon, 0));

		return packet;
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args)
	{
		if (executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(executer).equals(ImperatriceLumiereStyles.IMPERATRICE_SWORD))
		{
			SkillConsumeEvent event = new SkillConsumeEvent(executer, this, this.resource);
			executer.getEventListener().triggerEvents(PlayerEventListener.EventType.SKILL_CONSUME_EVENT, event);

			if (!event.isCanceled())
			{
				event.getResourceType().consumer.consume(this, executer, event.getAmount());
			}

			if (executer.getEventListener().triggerEvents(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, new BasicAttackEvent(executer)))
			{
				return;
			}

			CapabilityItem cap = executer.getHoldingItemCapability(InteractionHand.MAIN_HAND);
			StaticAnimation attackMotion = null;
			ServerPlayer player = executer.getOriginal();
			SkillContainer skillContainer = executer.getSkill(this);
			SkillDataManager dataManager = skillContainer.getDataManager();
			int comboCounter = dataManager.getDataValue(EFBSDataKeys.BLAZE_COMBO.get());

			if (player.isPassenger())
			{
				Entity entity = player.getVehicle();

				if ((entity instanceof PlayerRideableJumping ridable && ridable.canJump()) && cap.availableOnHorse() && cap.getMountAttackMotion() != null)
				{
					comboCounter %= cap.getMountAttackMotion().size();
					attackMotion = cap.getMountAttackMotion().get(comboCounter).get();
					comboCounter++;
				}
			} else
			{
				int fw = args.readInt();
				int sw = args.readInt();
				int comboSize;
				boolean dashAttack = player.isSprinting();

				if (dashAttack)
				{
					// Dash Attack
					LogUtils.getLogger().debug("Dash Attack");
					attackMotion = DASH_ATTACK.get();
					comboCounter = 0;
				}
				else if (sw == -1)
				{
					// Right Attack
					LogUtils.getLogger().debug("Right Tilt");
					comboCounter = 0;
					attackMotion = RTILT.get();
				}
				else if (sw == 1)
				{
					// Left Attack
					LogUtils.getLogger().debug("Left Tilt");
					comboCounter = 0;
					attackMotion = LTILT.get();
				}
				else if (fw == -1)
				{
					// Back Attack
					LogUtils.getLogger().debug("Back Tilt");
					attackMotion = BTILT.get();
					comboCounter = 0;
				}
				else if (fw == 1)
				{
					//Forward Tilt
					LogUtils.getLogger().debug("Forward Tilt");
					attackMotion = FTILT.get();
					comboCounter = 0;
				}
				else if (player.isShiftKeyDown())
				{
					//Down Tilt
					LogUtils.getLogger().debug("Down Tilt");
					attackMotion = CROUCH_LIGHT.get();
					comboCounter = 0;
				}
				else
				{
					// Normal Attack
					comboSize = IMPERATRICE_SWORD_JAB_SET.size();
					comboCounter %= comboSize;
					LogUtils.getLogger().debug("Jab");
					LogUtils.getLogger().debug("Combo Counter: {}", comboCounter);
					attackMotion = IMPERATRICE_SWORD_JAB_SET.get(comboCounter).get();
					comboCounter++;
				}
			}

			setComboCounterWithEvent(ComboCounterHandleEvent.Causal.ACTION_ANIMATION_RESET, executer, skillContainer, attackMotion, comboCounter);

			if (attackMotion != null)
			{
				executer.playAnimationSynchronized(attackMotion, 0);
			}

			executer.updateEntityState();
		}
		else
		{
			super.executeOnServer(executer, args);
		}

	}
}
