package net.forixaim.battle_arts;


import net.forixaim.battle_arts.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.battle_arts.core_assets.capabilities.WeaponTypeInjection;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;

import net.forixaim.battle_arts.initialization.registry.CreativeTabRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.main.EpicFightExtensions;
import yesman.epicfight.world.capabilities.item.WeaponCategory;


import static net.forixaim.battle_arts.initialization.registry.BlockRegistry.BLOCKS;
import static net.forixaim.battle_arts.initialization.registry.CreativeTabRegistry.CREATIVE_MODE_TABS;
import static net.forixaim.battle_arts.initialization.registry.ItemRegistry.ITEMS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EpicFightBattleArts.MOD_ID)
public class EpicFightBattleArts
{

	public static final String MOD_ID = "battle_arts";

	public EpicFightBattleArts()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		WeaponCategory.ENUM_MANAGER.registerEnumCls(MOD_ID, BattleStyleCategories.class);
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
		BattleArtsDataKeys.DATA_KEYS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
		ModLoadingContext.get().registerExtensionPoint(EpicFightExtensions.class, () ->
				new EpicFightExtensions(CreativeTabRegistry.MAIN_ITEMS.get()));
	}

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event)
	{

	}

	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents
	{
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event)
		{

		}
	}
}
