package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
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
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, event ->
		{
			//This is the durability damage function since you go into overdrive and your weapons all melt.
			ItemStack mainHandItem = event.getPlayerPatch().getOriginal().getItemInHand(InteractionHand.MAIN_HAND);
			if (mainHandItem.isDamageableItem() && mainHandItem.getItem() instanceof TieredItem tieredItem && container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) >= 200)
			{
				if (tieredItem.getTier().equals(Tiers.WOOD))
				{
					mainHandItem.setDamageValue(mainHandItem.getDamageValue() + 3);
				}
				if (tieredItem.getTier().equals(Tiers.STONE))
				{
					mainHandItem.setDamageValue(mainHandItem.getDamageValue() + 2);
				}
				if (tieredItem.getTier().equals(Tiers.GOLD) || tieredItem.getTier().equals(Tiers.IRON))
				{
					mainHandItem.setDamageValue(mainHandItem.getDamageValue() + 1);
				}
			}
		});
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		executor.playAnimationSynchronized(provider.get(), 0.0f);
		executor.getSkill(this).getDataManager().setData(EFBSDataKeys.HEAT.get(), 1000);
		this.setMaxDurationSynchronize(executor, 1000);
		this.setDurationSynchronize(executor, executor.getSkill(this).getDataManager().getDataValue(EFBSDataKeys.HEAT.get()));
		super.executeOnServer(executor, args);
	}

	@Override
	public void onReset(SkillContainer container)
	{
		super.onReset(container);
	}
}
