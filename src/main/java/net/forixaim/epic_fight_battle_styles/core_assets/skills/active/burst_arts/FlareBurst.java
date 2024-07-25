package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import yesman.epicfight.world.entity.eventlistener.SkillExecuteEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class FlareBurst extends BurstArt
{
	private static final UUID EVENT_UUID = UUID.fromString("1f198bcc-af51-45ae-a5f0-78496ec47408");
	private final AnimationProvider<ActionAnimation> provider;

	private final Consumer<SkillExecuteEvent> skillExecuteEvent = event ->
	{
	};

	public FlareBurst(Builder<? extends Skill> builder)
	{
		super(builder);
		provider = () -> (ActionAnimation) BattleAnimations.IMPERATRICE_BLAZE_BLASTER;
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executer)
	{
		return executer.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE);
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		executor.playAnimationSynchronized(provider.get(), 0.0f);
		executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.HEAT.get(), 1000, executor.getOriginal());
		executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.FLARE_BURST.get(), true, executor.getOriginal());

		super.executeOnServer(executor, args);
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		super.updateContainer(container);
		if (container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) >= 200 && container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()))
		{
			container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setData(EFBSDataKeys.FLARE_BURST.get(), false);
		}
	}

	@Override
	public void onReset(SkillContainer container)
	{
		super.onReset(container);
	}
}
