package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.items.equipment.ModularChestpiece;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import mekanism.common.Mekanism;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.gameObjs.items.armor.DMArmor;
import moze_intel.projecte.gameObjs.items.armor.GemArmorBase;
import moze_intel.projecte.gameObjs.items.armor.RMArmor;
import net.forixaim.epic_fight_battle_styles.Config;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.client.gui.BattleModeGui;
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
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;

import java.util.Objects;
import java.util.UUID;

public class ImperatriceLumiere extends BattleStyle
{
	private float speedBonus;
	private float damageBonus;
	private ItemStack onHandItem;
	private int tick = 0;
	private int auraTick = 0;
	private static final UUID EVENT_UUID = UUID.fromString("fceabee5-64fc-40dd-a7a2-4470ed8ff00a");
	private static final UUID FLARE_BURST_ATTACK_AMPLIFICATION_UUID = UUID.fromString("3844e15e-8d21-47a2-8e4a-136f47961105");
	private static final CapabilityItem.WeaponCategories[] AVAILABLE_WEAPON_TYPES = {
			CapabilityItem.WeaponCategories.SWORD,
			CapabilityItem.WeaponCategories.LONGSWORD
	};

	private static final Pair<AnimationProvider<AttackAnimation>, AnimationProvider<AttackAnimation>> RIPOSTES = new Pair<>(
			() -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_DASH_RIPOSTE,
			() -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_DASH_ATTACK
	);

	private static final AttackAnimationProvider JOYEUSE_DRAW = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_JOYEUSE_DRAW;

	public void triggerIgnitionRiposte(PlayerPatch<?> playerPatch, SkillSlot originSlot, @NotNull DamageSource source)
	{
		if (originSlot == SkillSlots.DODGE)
		{
			playerPatch.getOriginal().teleportTo(Objects.requireNonNull(source.getEntity()).getX(), source.getEntity().getY(), source.getEntity().getZ());
			playerPatch.playAnimationSynchronized(RIPOSTES.getFirst().get(), 0);
			playerPatch.updateEntityState();
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
					if (event.getDamageSource().getAnimation() == BattleAnimations.IMPERATRICE_SWORD_JAB3)
					{
						if (EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class) != null)
						{
							EpicFightCapabilities.getEntityPatch(event.getTarget(), LivingEntityPatch.class).playSound(SoundRegistry.CRITICAL_HIT_2.get(), 0, 0);
						}
					}
					if (container.getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()))
					{
						event.getDamageSource().addExtraDamage(new ExtraDamageInstance(new ExtraDamageInstance.ExtraDamage(
								((a, b, c, d, e) -> 3),
								(a, b, c, d) -> {}
						)));
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

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldDraw(SkillContainer container) {
		return true;
	}

	private ResourceLocation UIFlareBurst(SkillContainer container)
	{
		if (Minecraft.getInstance().getUser().getUuid().equals("42479ed5a8f04967bfb17500577896a6"))
		{
			return container.getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()) ? new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/heat_icon.png") : new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/heat_icon_burst.png");
		}
		else
		{
			return container.getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()) ? new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/heat_icon_burst.png") : new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/heat_icon.png");
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.translate(0, (float)gui.getSlidingProgression(), 0);
		guiGraphics.blit(UIFlareBurst(container), (int)x, (int)y, 24, 24, 0, 0, 1, 1, 1, 1);
		Integer Heat = container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get());
		String Heat_Level = Heat >= 1000 ? String.format("%.2f", (float)Heat / 1000) + "K" : Heat.toString();
		guiGraphics.drawString(gui.font, Heat_Level, x + 4, y + 6, 16777215, true);
		poseStack.popPose();
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		super.updateContainer(container);
		tick++;

		if (container.getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()))
			auraTick++;

		if (tick == 4)
		{
			if (container.getDataManager().hasData(EFBSDataKeys.HEAT.get()) && container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) > 0)
			{
				if (container.getExecuter() instanceof LocalPlayerPatch localPlayerPatch)
				{
					container.getDataManager().setDataSync(EFBSDataKeys.HEAT.get(), container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) - 1, localPlayerPatch.getOriginal());
				}
				else if (container.getExecuter() instanceof ServerPlayerPatch serverPlayerPatch)
				{
					container.getDataManager().setDataSync(EFBSDataKeys.HEAT.get(), container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) - 1, serverPlayerPatch.getOriginal());

				}

			}
			tick = 0;
		}

		if (auraTick >= 80)
		{
			LogUtils.getLogger().debug("Flare Burst Aura Sound Attempt Play");

			if (container.getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()))
			{
				container.getExecuter().playSound(SoundRegistry.FLARE_BURST_AURA.get(), 0,0);
			}
			auraTick = 0;
		}

		if (container.getDataManager().hasData(EFBSDataKeys.HEAT.get()) && container.getDataManager().getDataValue(EFBSDataKeys.HEAT.get()) > 200)
		{
			for (int i = 0; i < 3; i++)
			{
				RandomSource random = container.getExecuter().getOriginal().getRandom();
				double x = container.getExecuter().getOriginal().getX() + (random.nextDouble() - random.nextDouble());
				double y = container.getExecuter().getOriginal().getY() + 1 + (random.nextDouble() - random.nextDouble());
				double z = container.getExecuter().getOriginal().getZ() + (random.nextDouble() - random.nextDouble());
				container.getExecuter().getOriginal().level().addParticle(Minecraft.getInstance().getUser().getUuid().equals("42479ed5a8f04967bfb17500577896a6") ? ParticleTypes.FLAME : ParticleTypes.SOUL_FIRE_FLAME, x, y, z, (random.nextDouble() - random.nextDouble()) * 0.05, 0.15, (random.nextDouble() - random.nextDouble()) * 0.05);
			}


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
		if (container.getExecuter() instanceof LocalPlayerPatch)
		{
			EpicFightNetworkManager.sendToServer(new CPChangeSkill(SkillSlots.BASIC_ATTACK.universalOrdinal(), -1, EpicFightSkills.BASIC_ATTACK.toString(), false));
		}
		else if (container.getExecuter() instanceof ServerPlayerPatch)
		{
			EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(SkillSlots.BASIC_ATTACK, EpicFightSkills.BASIC_ATTACK.toString(), SPChangeSkill.State.ENABLE), (ServerPlayer) container.getExecuter().getOriginal());
		}
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
