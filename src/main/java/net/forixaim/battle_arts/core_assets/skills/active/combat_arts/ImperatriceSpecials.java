package net.forixaim.battle_arts.core_assets.skills.active.combat_arts;

import io.netty.buffer.Unpooled;
import net.forixaim.battle_arts.core_assets.animations.BattleAnimations;
import net.forixaim.battle_arts.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillSlots;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class ImperatriceSpecials extends CombatArt
{

	private static final UUID EVENT_UUID = UUID.fromString("a4deb3a3-2eb2-4e3b-8204-265e95cc4eaf");
	private static final AnimationProvider<AttackAnimation> provider = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_INCANDESCENT_FIREWORK.get();
	private static final AnimationProvider<AttackAnimation> INFERNO_ASTROLABE = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_INFERNO_ASTROLABE;

	public ImperatriceSpecials(Builder<? extends Skill> builder)
	{
		super(builder);
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
	public void onInitiate(SkillContainer container)
	{
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, event ->
		{
			if (event.getAnimation().equals(provider.get()) || event.getAnimation().equals(INFERNO_ASTROLABE.get()))
			{
				event.getPlayerPatch().getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(BattleArtsDataKeys.SPECIAL_EXECUTING.get(), false, event.getPlayerPatch().getOriginal());
			}
		});
		super.onInitiate(container);
	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executer)
	{
		return executer.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) &&
				!executer.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(BattleArtsDataKeys.SPECIAL_EXECUTING.get()) &&
				executer.getSkill(SkillSlots.WEAPON_INNATE).getDataManager().hasData(BattleArtsDataKeys.CHARGE_EXECUTING.get()) && !executer.getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(BattleArtsDataKeys.CHARGE_EXECUTING.get()) &&
				!executer.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(BattleArtsDataKeys.ULTIMATE_ART_ACTIVE.get());
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		int forwardBack = args.readInt();
		int leftRight = args.readInt();

		executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(BattleArtsDataKeys.SPECIAL_EXECUTING.get(), true, executor.getOriginal());

		if (forwardBack == 1 && executor.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(executor) == ImperatriceLumiereStyles.IMPERATRICE_SWORD)
		{
			executor.playAnimationSynchronized(INFERNO_ASTROLABE.get(), 0);
		}
		else
		{
			executor.playAnimationSynchronized(provider.get(), 0);
		}
	}
}
