package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.items.equipment.ModularChestpiece;
import com.mojang.logging.LogUtils;
import mekanism.common.Mekanism;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.gameObjs.items.armor.DMArmor;
import moze_intel.projecte.gameObjs.items.armor.GemArmorBase;
import moze_intel.projecte.gameObjs.items.armor.RMArmor;
import net.forixaim.epic_fight_battle_styles.Config;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPChangeSkill;
import yesman.epicfight.network.server.SPChangeSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ImperatriceLumiere extends BattleStyle
{
	private float speedBonus;
	private float damageBonus;
	private ItemStack onHandItem;
	private int tick = 0;
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
		associatedBasicAttack = SkillRegistry.IMPERATRICE_ATTACK;
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, event ->
		{
			if (!container.getExecuter().getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK))
			{
				container.requestExecute((ServerPlayerPatch) container.getExecuter(), null);
			}
		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, event ->
		{

		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, event ->
		{
			if (event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK))
			{
				if (event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().getDataValue(EFBSDataKeys.JAB.get()))
				{
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(EFBSDataKeys.JAB.get(), false);
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(EFBSDataKeys.BLAZE_COMBO.get(), 0);
				}
				if (event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().getDataValue(EFBSDataKeys.FTILT.get()))
				{
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(EFBSDataKeys.FTILT.get(), false);
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(EFBSDataKeys.CERCLE_DE_FEU.get(), 0);
				}
			}
		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_ATTACK, EVENT_UUID, event ->
		{
			if (event.getPlayerPatch().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).is(ItemRegistry.ORIGIN_JOYEUSE.get()))
			{
				event.getDamageSource().addRuntimeTag(DamageTypeTags.BYPASSES_INVULNERABILITY);
				event.getDamageSource().addRuntimeTag(DamageTypeTags.BYPASSES_ENCHANTMENTS);
			}
			if (Config.triggerAntiCheese)
			{
				boolean cheeseFound = false;
				if (event.getTarget() instanceof Player player && !player.isCreative())
				{
					cheeseFound = isCheeseFound(event, cheeseFound);
					if (cheeseFound)
					{
						EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).playSound(SoundEvents.ITEM_BREAK, 1f, 0, 0);
						EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).playSound(SoundRegistry.CHEESE.get(), 0, 0);
						EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).playSound(SoundRegistry.IMPERATRICE_ANTI_CHEESE.get(), 0, 0);
					}
				}
				else if (!(event.getTarget() instanceof Player))
				{
					cheeseFound = isCheeseFound(event, cheeseFound);
					if (cheeseFound)
					{
						EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class).playSound(SoundEvents.ITEM_BREAK, 1f, 0, 0);
						EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class).playSound(SoundRegistry.CHEESE.get(), 0, 0);
						EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class).playSound(SoundRegistry.IMPERATRICE_ANTI_CHEESE.get(), 0, 0);
					}
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
						if (ThreadLocalRandom.current().nextInt(0, 4) == 1)
						{
							event.getDamageSource().setArmorNegation(Float.MAX_VALUE);
							event.getDamageSource().setStunType(StunType.NEUTRALIZE);
							event.getDamageSource().setDamageModifier(ValueModifier.setter(event.getAttackDamage() * 1.5f));
							event.getPlayerPatch().playSound(SoundRegistry.CRITICAL_HIT_2.get(), 0, 0);
						}
					}
				});
		super.onInitiate(container);
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executor)
	{
		return super.canExecute(executor);
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		if (!executor.getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK))
		{
			executor.getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(SkillRegistry.IMPERATRICE_ATTACK);
			EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(SkillSlots.BASIC_ATTACK, SkillRegistry.IMPERATRICE_ATTACK.toString(), SPChangeSkill.State.ENABLE), executor.getOriginal());
		}
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		super.updateContainer(container);
		tick++;
		if (tick == 20)
		{
			if (container.getDataManager().hasData(EFBSDataKeys.HEAT.get()) && container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) > 0)
			{
				container.getDataManager().setData(EFBSDataKeys.HEAT.get(), container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) - 1);
			}
			LogUtils.getLogger().debug("Heat Level: {}", container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()));
			tick = 0;
		}


	}

	private boolean isCheeseFound(DealtDamageEvent.Attack event, boolean cheeseFound)
	{
		for (ItemStack item : event.getTarget().getArmorSlots())
		{
			if (ModList.get().isLoaded(Mekanism.MODID) && item.getItem() instanceof ItemMekaSuitArmor)
			{
				//Trigger Anti-Invincibility Cheese
				item.copyAndClear();
				cheeseFound = true;
			}
			if (ModList.get().isLoaded(DraconicEvolution.MODID) && item.getItem() instanceof ModularChestpiece)
			{
				//Trigger Anti-Invincibility Cheese
				item.copyAndClear();
				cheeseFound = true;
			}
			if (ModList.get().isLoaded(ProjectEAPI.PROJECTE_MODID) && (item.getItem() instanceof DMArmor || item.getItem() instanceof RMArmor || item.getItem() instanceof GemArmorBase))
			{
				//Trigger Anti-Invincibility Cheese
				item.copyAndClear();
				cheeseFound = true;
			}
		}
		return cheeseFound;
	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		super.onRemoved(container);
		container.getExecuter().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.BASIC_ATTACK);
		EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(SkillSlots.BASIC_ATTACK, EpicFightSkills.BASIC_ATTACK.toString(), SPChangeSkill.State.ENABLE), (ServerPlayer) container.getExecuter().getOriginal());
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_ATTACK, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
	}

	public ImperatriceLumiere(Skill.Builder<? extends Skill> builder)
	{
		super(builder);
	}
}
