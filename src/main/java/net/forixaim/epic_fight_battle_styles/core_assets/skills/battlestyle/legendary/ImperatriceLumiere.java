package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary;

import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;

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



	@Override
	public void onInitiate(SkillContainer container)
	{
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, event ->
		{
			if (!event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK))
			{
				container.getExecuter().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(SkillRegistry.IMPERATRICE_ATTACK);
			}
			if (event.getPlayerPatch().isBattleMode() && event.getPlayerPatch().isAirborneState())
				container.getDataManager().setData(EFBSDataKeys.IN_AIR.get(), true);
			else
				container.getDataManager().setData(EFBSDataKeys.IN_AIR.get(), false);

		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, event ->
		{
			if (event.getPlayerPatch().isBattleMode())
			{
				if (event.getMovementInput().jumping)
					container.getDataManager().setData(EFBSDataKeys.UP.get(), true);
				else
					container.getDataManager().setData(EFBSDataKeys.UP.get(), false);

				if (event.getMovementInput().up)
					container.getDataManager().setData(EFBSDataKeys.FORWARD.get(), true);
				else
					container.getDataManager().setData(EFBSDataKeys.FORWARD.get(), false);

				if (event.getMovementInput().down)
					container.getDataManager().setData(EFBSDataKeys.BACKWARD.get(), true);
				else
					container.getDataManager().setData(EFBSDataKeys.BACKWARD.get(), false);

				if (event.getMovementInput().left)
					container.getDataManager().setData(EFBSDataKeys.LEFT.get(), true);
				else
					container.getDataManager().setData(EFBSDataKeys.LEFT.get(), false);

				if (event.getMovementInput().right)
					container.getDataManager().setData(EFBSDataKeys.RIGHT.get(), true);
				else
					container.getDataManager().setData(EFBSDataKeys.RIGHT.get(), false);

				if (event.getMovementInput().shiftKeyDown)
					container.getDataManager().setData(EFBSDataKeys.DOWN.get(), true);
				else
					container.getDataManager().setData(EFBSDataKeys.DOWN.get(), false);
			}
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
							event.getTarget().setRemainingFireTicks(event.getTarget().getRemainingFireTicks() + 20);
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
