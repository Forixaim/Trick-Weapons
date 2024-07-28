package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts;

import com.mojang.blaze3d.vertex.PoseStack;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
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
	private static final AnimationProvider<AttackAnimation> FLARE_BURST_ACTIVATE = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_FLARE_BURST;
	private boolean ready = false;

	private final Consumer<SkillExecuteEvent> skillExecuteEvent = event ->
	{
	};

	public FlareBurst(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);
		container.setResource(container.getMaxResource());
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executer)
	{
		return executer.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) && executer.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && !(executer.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get())) && !executer.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get());
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		executor.playSound(SoundRegistry.BURST_ART_READY.get(), 0, 0);
		executor.playAnimationSynchronized(FLARE_BURST_ACTIVATE.get(), 0.0f);
		super.executeOnServer(executor, args);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldDraw(SkillContainer container) {
		return container.getResource() > 0 && container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && !container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get());
	}


	@OnlyIn(Dist.CLIENT)
	@Override
	public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.translate(0, (float)gui.getSlidingProgression(), 0);
		guiGraphics.blit(this.getSkillTexture(), (int)x, (int)y, 24, 24, 0, 0, 1, 1, 1, 1);
		float CD = container.getResource();
		String Cooldown_Time = (container.getMaxResource() - CD) >= 100 ? String.format("%.0f", container.getMaxResource() - CD) : String.format("%.1f", container.getMaxResource() - CD);
		guiGraphics.drawString(gui.font, Cooldown_Time, x + 4, y + 6, 16777215, true);
		poseStack.popPose();
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		super.updateContainer(container);
		if (container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()))
		{
			container.setResource(0);
			ready = false;
		}
		if (container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.HEAT.get()) && !(container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) >= 200) && container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()))
		{
			if (container.getExecuter() instanceof ServerPlayerPatch serverPlayerPatch)
			{
				container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.FLARE_BURST.get(), false, serverPlayerPatch.getOriginal());
			}
			else if (container.getExecuter() instanceof LocalPlayerPatch localPlayerPatch)
			{
				container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().setDataSync(EFBSDataKeys.FLARE_BURST.get(), false, localPlayerPatch.getOriginal());
			}

		}
	}

	@Override
	public void onReset(SkillContainer container)
	{
		super.onReset(container);
	}
}
