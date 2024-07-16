package net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate;

import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.common.Tags;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.EpicFightEntities;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class BlazeStingerSkill extends WeaponInnateSkill
{

	private static final UUID EVENT_UUID = UUID.fromString("9ed5a11f-c7b2-4679-99db-0a4c8de2f5a3");
	private AnimationProvider<AttackAnimation> sting;
	private final AttackAnimationProvider[] heavyAttacks = {
			() -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER //Standing

	};

	public BlazeStingerSkill(Builder<? extends Skill> builder)
	{
		super(builder);
		sting = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER;
	}

	@Override
	public void onInitiate(SkillContainer container) {
		super.onInitiate(container);

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID, (event) -> {
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

						event.getTarget().removeEffect(MobEffects.FIRE_RESISTANCE);
						event.getPlayerPatch().playSound(SoundRegistry.CRITICAL_HIT.get(), 0, 0);
					}
				}
			}
		});
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		if (executor.getOriginal().isShiftKeyDown())
		{

		}
		if (executor.getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK) && executor.getSkill(SkillSlots.BASIC_ATTACK).getDataManager().hasData(EFBSDataKeys.BLAZE_COMBO.get()))
		{
			executor.getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(EFBSDataKeys.BLAZE_COMBO.get(), 0);
		}
		executor.playAnimationSynchronized(sting.get(), 0.0F);
		super.executeOnServer(executor, args);
	}
}
