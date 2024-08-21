package net.forixaim.battle_arts.core_assets.skills.battlestyle.legendary.visitors_of_omneria;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.items.equipment.ModularChestpiece;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import mekanism.common.Mekanism;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.gameObjs.items.armor.DMArmor;
import moze_intel.projecte.gameObjs.items.armor.GemArmorBase;
import moze_intel.projecte.gameObjs.items.armor.RMArmor;
import net.forixaim.battle_arts.Config;
import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.animations.BattleAnimations;
import net.forixaim.battle_arts.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.legendary.LegendaryBattleStyle;
import net.forixaim.battle_arts.initialization.registry.ItemRegistry;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;
import net.forixaim.battle_arts.initialization.registry.SoundRegistry;
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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPChangeSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Objects;
import java.util.UUID;

public class ImperatriceLumiere extends LegendaryBattleStyle
{
	private int tick = 0;
	private int auraTick = 0;
	private static final UUID EVENT_UUID = UUID.fromString("fceabee5-64fc-40dd-a7a2-4470ed8ff00a");


	@Override
	public void onInitiate(SkillContainer container)
	{
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID, event ->
		{
			if (event.getMovementInput().up && !event.getMovementInput().shiftKeyDown)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.FORWARD_WINDOW.get(), 14, event.getPlayerPatch().getOriginal());
			}
			if (event.getMovementInput().up && !event.getMovementInput().shiftKeyDown && container.getDataManager().getDataValue(BattleArtsDataKeys.FORWARD_WINDOW.get()) > 0)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.FORWARD_WINDOW.get(), 14, event.getPlayerPatch().getOriginal());
			}
			if (event.getMovementInput().up && event.getMovementInput().shiftKeyDown)
			{
				if (container.getDataManager().getDataValue(BattleArtsDataKeys.FORWARD_WINDOW.get()) > 0 && container.getDataManager().getDataValue(BattleArtsDataKeys.DOWN_WINDOW.get()) > 0)
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.SHORYUKEN_WINDOW.get(), 14, event.getPlayerPatch().getOriginal());
				}
				else
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.FORWARD_DOWN_WINDOW.get(), 14, event.getPlayerPatch().getOriginal());
				}
			}
		});
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, event ->
		{
			if (!event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK))
			{
				container.requestExecute(event.getPlayerPatch(), null);
			}
			container.getDataManager().setDataSync(BattleArtsDataKeys.FORWARD_WINDOW.get(), 0, event.getPlayerPatch().getOriginal());
			container.getDataManager().setDataSync(BattleArtsDataKeys.DOWN_WINDOW.get(), 0, event.getPlayerPatch().getOriginal());
			container.getDataManager().setDataSync(BattleArtsDataKeys.FORWARD_DOWN_WINDOW.get(), 0, event.getPlayerPatch().getOriginal());
			container.getDataManager().setDataSync(BattleArtsDataKeys.SHORYUKEN_WINDOW.get(), 0, event.getPlayerPatch().getOriginal());

		});

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, event ->
		{
			if (event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_ATTACK))
			{
				if (event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().getDataValue(BattleArtsDataKeys.JAB.get()))
				{
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(BattleArtsDataKeys.JAB.get(), false);
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(BattleArtsDataKeys.BLAZE_COMBO.get(), 0);
				}
				if (event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().getDataValue(BattleArtsDataKeys.FTILT.get()))
				{
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(BattleArtsDataKeys.FTILT.get(), false);
					event.getPlayerPatch().getSkill(SkillSlots.BASIC_ATTACK).getDataManager().setData(BattleArtsDataKeys.CERCLE_DE_FEU.get(), 0);
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
					if (event.getDamageSource().getAnimation() == BattleAnimations.IMPERATRICE_SWORD_NEUTRAL_AERIAL || event.getDamageSource().getAnimation() == BattleAnimations.IMPERATRICE_SWORD_FORWARD_AERIAL)
					{
						event.getPlayerPatch().getOriginal().addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, 4, true, false, false));
						event.getPlayerPatch().getOriginal().addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5, 20, true, false, false));
					}

					if (container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()))
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
		if (!executor.getSkill(SkillSlots.AIR_ATTACK).hasSkill(SkillRegistry.IMPERATRICE_AERIALS))
		{
			executor.getSkillCapability().skillContainers[SkillSlots.AIR_ATTACK.universalOrdinal()].setSkill(SkillRegistry.IMPERATRICE_AERIALS);
			EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(SkillSlots.AIR_ATTACK, SkillRegistry.IMPERATRICE_AERIALS.toString(), SPChangeSkill.State.ENABLE), executor.getOriginal());
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean shouldDraw(SkillContainer container)
	{
		return container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(container.getExecuter()) == ImperatriceLumiereStyles.IMPERATRICE_SWORD;
	}

	private ResourceLocation UIFlareBurst(SkillContainer container)
	{
		if (Minecraft.getInstance().getUser().getUuid().equals("42479ed5a8f04967bfb17500577896a6"))
		{
			return container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()) ? new ResourceLocation(EpicFightBattleArts.MOD_ID, "textures/gui/heat_icon.png") : new ResourceLocation(EpicFightBattleArts.MOD_ID, "textures/gui/heat_icon_burst.png");
		}
		else
		{
			return container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()) ? new ResourceLocation(EpicFightBattleArts.MOD_ID, "textures/gui/heat_icon_burst.png") : new ResourceLocation(EpicFightBattleArts.MOD_ID, "textures/gui/heat_icon.png");
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		poseStack.translate(0, (float)gui.getSlidingProgression(), 0);
		guiGraphics.blit(UIFlareBurst(container), (int)x-4, (int)y-4, 36, 36, 0, 0, 1, 1, 1, 1);
		Float Heat = container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get());
		String Heat_Level = Heat >= 1000 ? String.format("%.2f", Heat / 1000) + "K" : String.format("%.0f", Heat);
		guiGraphics.drawString(gui.font, Heat_Level, x + 4, y + 6, 16777215, true);
		poseStack.popPose();
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		if (container.getExecuter().getOriginal().isOnFire())
		{
			container.getExecuter().getOriginal().heal(0.125f);
			container.getExecuter().getOriginal().getFoodData().setFoodLevel(container.getExecuter().getOriginal().getFoodData().getFoodLevel() + 1);
			container.getExecuter().getOriginal().getFoodData().setSaturation(container.getExecuter().getOriginal().getFoodData().getSaturationLevel() + 1);
			if (ModList.get().isLoaded(IronsSpellbooks.MODID) && container.getExecuter().getOriginal().getAttributes().hasAttribute(AttributeRegistry.MAX_MANA.get()) && ClientMagicData.getPlayerMana() > Objects.requireNonNull(container.getExecuter().getOriginal().getAttribute(AttributeRegistry.MAX_MANA.get())).getValue())
			{
				ClientMagicData.setMana(ClientMagicData.getPlayerMana() + 2);
				if (ClientMagicData.getPlayerMana() > Objects.requireNonNull(container.getExecuter().getOriginal().getAttribute(AttributeRegistry.MAX_MANA.get())).getValue())
				{
					ClientMagicData.setMana((int) Objects.requireNonNull(container.getExecuter().getOriginal().getAttribute(AttributeRegistry.MAX_MANA.get())).getValue());
				}
			}

		}
		if (!container.getExecuter().isLogicalClient())
		{
			ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch) container.getExecuter();
			if (container.getDataManager().getDataValue(BattleArtsDataKeys.FORWARD_WINDOW.get()) > 0)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.FORWARD_WINDOW.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.FORWARD_WINDOW.get()) - 1, serverPlayerPatch.getOriginal());
			}
			if (container.getDataManager().getDataValue(BattleArtsDataKeys.DOWN_WINDOW.get()) > 0)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.DOWN_WINDOW.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.DOWN_WINDOW.get()) - 1, serverPlayerPatch.getOriginal());
			}
			if (container.getDataManager().getDataValue(BattleArtsDataKeys.FORWARD_DOWN_WINDOW.get()) > 0)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.FORWARD_DOWN_WINDOW.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.FORWARD_DOWN_WINDOW.get()) - 1, serverPlayerPatch.getOriginal());
			}
			if (container.getDataManager().getDataValue(BattleArtsDataKeys.SHORYUKEN_WINDOW.get()) > 0)
			{
				container.getDataManager().setDataSync(BattleArtsDataKeys.SHORYUKEN_WINDOW.get(), container.getDataManager().getDataValue(BattleArtsDataKeys.SHORYUKEN_WINDOW.get()) - 1, serverPlayerPatch.getOriginal());
			}

		}
		if (!container.getExecuter().isLogicalClient() && container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) > 0)
		{
			if (ModList.get().isLoaded(IronsSpellbooks.MODID) && ClientMagicData.getPlayerMana() > 0 && container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()))
			{
				ClientMagicData.setMana(ClientMagicData.getPlayerMana() - 1);
			}
			else
			{
				if (container.getExecuter().getOriginal().isInLava() && container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) < 400)
				{
					if (container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) < 400)
						container.getDataManager().setDataSync(BattleArtsDataKeys.HEAT.get(),
								container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) + 1f
								, (ServerPlayer) container.getExecuter().getOriginal());
				}
				else if (container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) > 800)
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.HEAT.get(),
							container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) - 1f
							, (ServerPlayer) container.getExecuter().getOriginal());
				}
				else if (container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) > 600)
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.HEAT.get(),
							container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) - 0.825f
							, (ServerPlayer) container.getExecuter().getOriginal());
				}
				else if (container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) > 400)
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.HEAT.get(),
							container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) - 0.75f
							, (ServerPlayer) container.getExecuter().getOriginal());
				}
				else if (container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) > 200)
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.HEAT.get(),
							container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) - 0.625f
							, (ServerPlayer) container.getExecuter().getOriginal());
				}
				else
				{
					container.getDataManager().setDataSync(BattleArtsDataKeys.HEAT.get(),
							container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) - 0.5f
							, (ServerPlayer) container.getExecuter().getOriginal());
				}
			}


		}
		tick += 1;
		if (tick == 40)
		{
			if (!container.getExecuter().isLogicalClient() && container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()) && !container.getExecuter().getOriginal().isCreative())
			{

				ItemStack heldItem = container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND);
				if (heldItem.isDamageableItem())
				{
					if (heldItem.getItem() instanceof TieredItem tieredItem)
					{
						if (tieredItem.getTier() == Tiers.NETHERITE)
						{
							RandomSource rand = container.getExecuter().getOriginal().getRandom();
							if (rand.nextInt(0, 5) == 0)
							{
								heldItem.setDamageValue(heldItem.getDamageValue() + 1);
								container.getExecuter().getOriginal().displayClientMessage(Component.translatable("text.battle_arts.item_melting_netherite"), true);
							}
						}
						else if (tieredItem.getTier() == Tiers.GOLD)
						{
							RandomSource rand = container.getExecuter().getOriginal().getRandom();
							if (rand.nextInt(0, 10) == 0)
							{
								heldItem.setDamageValue(heldItem.getDamageValue() + 1);
							}
						}
						else
						{
							heldItem.setDamageValue(heldItem.getDamageValue() + 1);
						}
					}
					else
					{
						container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).setDamageValue(container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getDamageValue() + 1);
					}

					if (container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getDamageValue() >= container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getMaxDamage())
					{

						if (container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof TieredItem tieredItem)
						{
							if (tieredItem.getTier() == Tiers.WOOD)
							{
								container.getExecuter().playSound(SoundEvents.FIRE_EXTINGUISH, 0, 0);
								container.getExecuter().getOriginal().displayClientMessage(Component.translatable("text.battle_arts.item_burn"), true);
							}
							else
							{
								container.getExecuter().playSound(SoundEvents.ITEM_BREAK, 0, 0);
								container.getExecuter().getOriginal().displayClientMessage(Component.translatable("text.battle_arts.item_melt"), true);
							}
						}
						else
						{
							container.getExecuter().playSound(SoundEvents.FIRE_EXTINGUISH, 0, 0);
							container.getExecuter().getOriginal().displayClientMessage(Component.translatable("text.battle_arts.item_melt"), true);
						}
						container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).copyAndClear();
					}
				}
			}
			tick = 0;
		}
		if (container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()))
			auraTick++;

		if (auraTick >= 80)
		{
			LogUtils.getLogger().debug("Flare Burst Aura Sound Attempt Play");

			if (container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()))
			{
				container.getExecuter().playSound(SoundRegistry.FLARE_BURST_AURA.get(), 0,0);
			}
			auraTick = 0;
		}

		if (container.getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get()) && container.getDataManager().getDataValue(BattleArtsDataKeys.HEAT.get()) > 200)
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
		container.getExecuter().getSkillCapability().skillContainers[SkillSlots.AIR_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.AIR_ATTACK);

		if (!container.getExecuter().isLogicalClient())
		{
			EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(SkillSlots.BASIC_ATTACK, EpicFightSkills.BASIC_ATTACK.toString(), SPChangeSkill.State.ENABLE), (ServerPlayer) container.getExecuter().getOriginal());
			EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(SkillSlots.AIR_ATTACK, EpicFightSkills.AIR_ATTACK.toString(), SPChangeSkill.State.ENABLE), (ServerPlayer) container.getExecuter().getOriginal());

		}
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_ATTACK, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
	}

	public ImperatriceLumiere(Skill.Builder<? extends Skill> builder)
	{
		super(builder);
		immuneDamages.add(DamageTypes.LAVA);
		immuneDamages.add(DamageTypes.FIREBALL);
		immuneDamages.add(DamageTypes.IN_FIRE);
		immuneDamages.add(DamageTypes.ON_FIRE);
		immuneDamages.add(DamageTypes.HOT_FLOOR);
		immuneDamages.add(DamageTypes.FALL);
		immuneDamages.add(DamageTypes.UNATTRIBUTED_FIREBALL);
	}
}
