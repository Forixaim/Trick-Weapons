package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary;

import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPChangeSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ImperatriceLumiere extends BattleStyle
{
	private float speedBonus;
	private float damageBonus;
	private ItemStack onHandItem;
	private boolean applied = false;
	private static final UUID EVENT_UUID = UUID.fromString("fceabee5-64fc-40dd-a7a2-4470ed8ff00a");
	private static final CapabilityItem.WeaponCategories[] AVAILABLE_WEAPON_TYPES = {
			CapabilityItem.WeaponCategories.SWORD,
			CapabilityItem.WeaponCategories.LONGSWORD
	};

	private static final AttackAnimationProvider JOYEUSE_DRAW = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_JOYEUSE_DRAW;

	public void triggerIgnitionRiposte(PlayerPatch<?> playerPatch, SkillSlot originSlot, @NotNull DamageSource source)
	{
		if (originSlot == SkillSlots.DODGE)
		{
			playerPatch.getOriginal().teleportTo(Objects.requireNonNull(source.getEntity()).getX(), source.getEntity().getY(), source.getEntity().getZ());
		}
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, event ->
		{
			if (!event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK))
			{
				container.getExecuter().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(SkillRegistry.IMPERATRICE_ATTACK);
				EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(SkillSlots.BASIC_ATTACK, SkillRegistry.IMPERATRICE_ATTACK.toString(), SPChangeSkill.State.ENABLE), event.getPlayerPatch().getOriginal());
			}
			if (event.getPlayerPatch().isBattleMode() && event.getPlayerPatch().isAirborneState())
				container.getDataManager().setData(EFBSDataKeys.IN_AIR.get(), true);
			else
				container.getDataManager().setData(EFBSDataKeys.IN_AIR.get(), false);

		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID, event ->
		{
			if (container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(container.getExecuter()) == ImperatriceLumiereStyles.IMPERATRICE_SWORD)
			{
				if (container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) < 100)
				{
					container.getDataManager().setDataSync(EFBSDataKeys.HEAT.get(), container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) + 1, (ServerPlayer) container.getExecuter().getOriginal());
				}
			}
		});
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID,
				event ->
				{
					if (container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).is(ItemRegistry.ORIGIN_JOYEUSE.get()))
					{
						if (ThreadLocalRandom.current().nextInt(0, 101) >= container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()))
						{
							event.getTarget().setRemainingFireTicks(event.getTarget().getRemainingFireTicks() + 20);
						}
					}
				});
		super.onInitiate(container);
	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		super.onRemoved(container);
		container.getExecuter().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.BASIC_ATTACK);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
	}

	public ImperatriceLumiere(Skill.Builder<? extends Skill> builder) {
		super(builder);
	}
}
