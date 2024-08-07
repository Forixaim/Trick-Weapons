package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.advanced;

import com.mojang.blaze3d.vertex.PoseStack;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.SamuraiStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class Ronin extends BattleStyle
{
	private static final UUID EVENT_UUID = UUID.fromString("55220562-9883-4a57-bd92-a6257127cb66");
	private boolean weaponChecked = false;

	public Ronin(Builder<? extends Skill> builder)
	{
		super(builder);
	}

	@Override
	public void onInitiate(SkillContainer container) {
		super.onInitiate(container);

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
			container.getDataManager().setDataSync(EFBSDataKeys.BATTO_SHEATH.get(), false, event.getPlayerPatch().getOriginal());
			event.getPlayerPatch().modifyLivingMotionByCurrentItem();
			container.getSkill().setConsumptionSynchronize(event.getPlayerPatch(), 0.0F);
			container.getSkill().setStackSynchronize(event.getPlayerPatch(), 0);
		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
			this.onReset(container);
		});
	}

	@Override
	public void onRemoved(SkillContainer container) {
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID);
	}

	@Override
	public boolean shouldDraw(SkillContainer container)
	{
		return container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.UCHIGATANA;
	}

	private ResourceLocation battojutsuReady(SkillContainer container)
	{
		return container.getDataManager().getDataValue(EFBSDataKeys.BATTO_SHEATH.get()) ? new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/skills/battojutsu_sheathed.png") : new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/skills/battojutsu.png");
	}

	@Override
	public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y)
	{
		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.translate(0, (float)gui.getSlidingProgression(), 0);
		guiGraphics.blit(this.battojutsuReady(container), (int)x, (int)y, 24, 24, 0, 0, 1, 1, 1, 1);
		float CD = container.getResource();
		String Cooldown_Time = (container.getMaxResource() - CD) > 100 ? String.format("%.0f", container.getMaxResource() - CD) : String.format("%.1f", container.getMaxResource() - CD);
		if (!(container.getMaxResource() - CD == 5))
		{
			guiGraphics.drawString(gui.font, Cooldown_Time, x + 4, y + 6, 16777215, true);
		}
		poseStack.popPose();
	}

	@Override
	public void onReset(SkillContainer container) {
		PlayerPatch<?> executer = container.getExecuter();

		if (!executer.isLogicalClient())
		{
			if (container.getDataManager().getDataValue(SkillDataKeys.SHEATH.get())) {
				ServerPlayerPatch playerpatch = (ServerPlayerPatch)executer;
				container.getDataManager().setDataSync(SkillDataKeys.SHEATH.get(), false, playerpatch.getOriginal());
				playerpatch.modifyLivingMotionByCurrentItem();
				container.getSkill().setConsumptionSynchronize(playerpatch, 0);
			}
		}
	}

	@Override
	public void setConsumption(SkillContainer container, float value) {
		PlayerPatch<?> executer = container.getExecuter();

		if (!executer.isLogicalClient() &&
			(
				executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(executer) == SamuraiStyles.SAMURAI_UCHIGATANA ||
				executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(executer) == SamuraiStyles.SAMURAI_UCHIGATANA_SHEATHED
			)
		)
		{
			if (container.getMaxResource() < value && !container.getDataManager().getDataValue(EFBSDataKeys.BATTO_SHEATH.get()))
			{
				ServerPlayer serverPlayer = (ServerPlayer) executer.getOriginal();
				container.getDataManager().setDataSync(EFBSDataKeys.BATTO_SHEATH.get(), true, serverPlayer);
				((ServerPlayerPatch)container.getExecuter()).modifyLivingMotionByCurrentItem();
				SPPlayAnimation msg3 = new SPPlayAnimation(Animations.BIPED_UCHIGATANA_SCRAP, serverPlayer.getId(), 0.0F);
				EpicFightNetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg3, serverPlayer);
			}
		}

		super.setConsumption(container, value);
	}

	@Override
	public float getCooldownRegenPerSecond(PlayerPatch<?> player) {
		return (player.getOriginal().isUsingItem() && player.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.UCHIGATANA) ? 0.0F : 1.0F;
	}
}
