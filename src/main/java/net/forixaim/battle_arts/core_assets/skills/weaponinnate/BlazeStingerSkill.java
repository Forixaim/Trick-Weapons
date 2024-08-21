package net.forixaim.battle_arts.core_assets.skills.weaponinnate;

import io.netty.buffer.Unpooled;
import net.forixaim.battle_arts.core_assets.animations.AnimationHelpers;
import net.forixaim.battle_arts.core_assets.animations.BattleAnimations;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillSlots;
import net.forixaim.battle_arts.initialization.registry.ItemRegistry;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;
import net.forixaim.battle_arts.initialization.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class BlazeStingerSkill extends WeaponInnateSkill
{

	private static final UUID EVENT_UUID = UUID.fromString("9ed5a11f-c7b2-4679-99db-0a4c8de2f5a3");
	private static final AnimationProvider<AttackAnimation> NEUTRAL_SMASH = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER;
	private static final AnimationProvider<AttackAnimation> DOWN_SMASH =  () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_DOWN_SMASH;
	private static final AnimationProvider<AttackAnimation> NEUTRAL_AERIAL_SMASH = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_NEUTRAL_HEAVY_AERIAL;
	private static final AnimationProvider<AttackAnimation> SOLAR_FLARE = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_SOLAR_FLARE;

	private final AttackAnimationProvider[] heavyAttacks = {
			() -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER //Standing

	};

	public BlazeStingerSkill(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	public static Builder createBlazeStinger()
	{
		return (new Builder().setCategory(SkillCategories.WEAPON_INNATE).setResource(Resource.STAMINA));
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
	public void onInitiate(SkillContainer container) {
		super.onInitiate(container);

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, event ->
		{
			if (event.getAnimation() == BattleAnimations.IMPERATRICE_SWORD_NEUTRAL_HEAVY_AERIAL || event.getAnimation() == BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER || event.getAnimation() == BattleAnimations.IMPERATRICE_SWORD_DOWN_SMASH ||  event.getAnimation() == BattleAnimations.IMPERATRICE_SWORD_SOLAR_FLARE)
				container.getDataManager().setDataSync(BattleArtsDataKeys.CHARGE_EXECUTING.get(), false, event.getPlayerPatch().getOriginal());
		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, event ->
		{
			if (container.getDataManager().getDataValue(BattleArtsDataKeys.CHARGE_EXECUTING.get()))
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.CHARGE_EXECUTING.get(), false, event.getPlayerPatch().getOriginal());
			}
		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID, (event) -> {
			if (event.getDamageSource().getAnimation() == BattleAnimations.IMPERATRICE_SWORD_DOWN_SMASH)
			{
				if (EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class) != null)
				{
					EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class).knockBackEntity(event.getTarget().getPosition(0), 1000);
				}
			}

			if (event.getDamageSource().getAnimation() == BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER) {
				ValueModifier damageModifier = ValueModifier.empty();
				this.getProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, this.properties.get(0)).ifPresent(damageModifier::merge);
				damageModifier.merge(ValueModifier.multiplier(0.8F));
				if (container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).is(ItemRegistry.ORIGIN_JOYEUSE.get()))
				{
					event.getTarget().setRemainingFireTicks(event.getTarget().getRemainingFireTicks() + 20);
				}
				if (event.getTarget().hasEffect(MobEffects.DAMAGE_RESISTANCE) || event.getTarget().hasEffect(MobEffects.FIRE_RESISTANCE))
				{
					if (ThreadLocalRandom.current().nextInt(0, 4) == 1)
					{
						if (event.getTarget().hasEffect(MobEffects.DAMAGE_RESISTANCE))
						{
							int duration = Objects.requireNonNull(event.getTarget().getEffect(MobEffects.DAMAGE_RESISTANCE)).getDuration();
							int potency = Objects.requireNonNull(event.getTarget().getEffect(MobEffects.DAMAGE_RESISTANCE)).getAmplifier() - 1;
							event.getTarget().removeEffect(MobEffects.DAMAGE_RESISTANCE);
							if (potency >= 0)
							{
								event.getTarget().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, potency));
							}
						}
						event.getDamageSource().addRuntimeTag(DamageTypeTags.BYPASSES_ENCHANTMENTS);
						event.getDamageSource().addRuntimeTag(DamageTypeTags.BYPASSES_ARMOR);
						event.getTarget().removeEffect(MobEffects.FIRE_RESISTANCE);
						event.getPlayerPatch().playSound(SoundRegistry.CRITICAL_HIT.get(), 0, 0);
					}
				}

			}
		});
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executor)
	{
		if (executor.isLogicalClient())
			return super.canExecute(executor);
		ItemStack itemstack = executor.getOriginal().getItemInHand(InteractionHand.MAIN_HAND);
		if (AnimationHelpers.isInAir(executor))
		{
			return EpicFightCapabilities.getItemStackCapability(itemstack).getInnateSkill(executor, itemstack) == this && executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().hasData(BattleArtsDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
					!executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(BattleArtsDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
					!executor.getSkill(this).getDataManager().getDataValue(BattleArtsDataKeys.CHARGE_EXECUTING.get()) && !executor.getSkill(this).getDataManager().getDataValue(BattleArtsDataKeys.CHARGE_AERIAL.get());
		}
		return EpicFightCapabilities.getItemStackCapability(itemstack).getInnateSkill(executor, itemstack) == this && executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().hasData(BattleArtsDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
				!executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(BattleArtsDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
				!executor.getSkill(this).getDataManager().getDataValue(BattleArtsDataKeys.CHARGE_EXECUTING.get());
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		int fw = args.readInt();
		int sw = args.readInt();

		executor.getSkill(this).getDataManager().setDataSync(BattleArtsDataKeys.CHARGE_EXECUTING.get(), true, executor.getOriginal());

		if (executor.getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK) && executor.getSkill(SkillSlots.BASIC_ATTACK).getDataManager().hasData(BattleArtsDataKeys.BLAZE_COMBO.get()))
		{
			executor.getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(BattleArtsDataKeys.BLAZE_COMBO.get(), 0);
		}
		if (AnimationHelpers.isInAir(executor))
		{
			executor.getSkill(this).getDataManager().setDataSync(BattleArtsDataKeys.CHARGE_AERIAL.get(), true, executor.getOriginal());
			executor.playAnimationSynchronized(NEUTRAL_AERIAL_SMASH.get(), 0);
		}
		else
		{
			if (fw == 1)
			{
				executor.playAnimationSynchronized(SOLAR_FLARE.get(), 0);
			}
			else if (executor.getOriginal().isShiftKeyDown())
			{
				executor.playAnimationSynchronized(DOWN_SMASH.get(), 0);
			}
			else
			{
				executor.playAnimationSynchronized(NEUTRAL_SMASH.get(), 0.0F);
			}
		}

		super.executeOnServer(executor, args);
	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		PlayerEventListener eventListener = container.getExecuter().getEventListener();
		eventListener.removeListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
		eventListener.removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID);
		eventListener.removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID);
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		if (!AnimationHelpers.isInAir(container.getExecuter()) && container.getDataManager().getDataValue(BattleArtsDataKeys.CHARGE_AERIAL.get()))
		{
			if (!container.getExecuter().isLogicalClient())
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.CHARGE_AERIAL.get(), false, (ServerPlayer) container.getExecuter().getOriginal());
			}
		}
	}
}
