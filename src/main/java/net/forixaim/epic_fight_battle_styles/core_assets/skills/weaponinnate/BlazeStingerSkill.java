package net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate;

import io.netty.buffer.Unpooled;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
		return executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
				!executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get());
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{

		if (executor.getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK) && executor.getSkill(SkillSlots.BASIC_ATTACK).getDataManager().hasData(EFBSDataKeys.BLAZE_COMBO.get()))
		{
			executor.getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(EFBSDataKeys.BLAZE_COMBO.get(), 0);
		}
		if (executor.getOriginal().isShiftKeyDown())
		{
			executor.playAnimationSynchronized(DOWN_SMASH.get(), 0);
		}
		else
		{
			executor.playAnimationSynchronized(NEUTRAL_SMASH.get(), 0.0F);
		}
		super.executeOnServer(executor, args);
	}
}