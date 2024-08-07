package net.forixaim.epic_fight_battle_styles;


import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.WeaponTypeInjection;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.AnimationRegistry;

import net.forixaim.epic_fight_battle_styles.initialization.registry.SoundRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.item.WeaponCategory;


import static net.forixaim.epic_fight_battle_styles.initialization.registry.BlockRegistry.BLOCKS;
import static net.forixaim.epic_fight_battle_styles.initialization.registry.CreativeTabRegistry.CREATIVE_MODE_TABS;
import static net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry.ITEMS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EpicFightBattleStyles.MOD_ID)
public class EpicFightBattleStyles {

    public static final String MOD_ID = "epic_fight_battle_styles";

    public EpicFightBattleStyles() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        SkillCategory.ENUM_MANAGER.registerEnumCls(MOD_ID, EpicFightBattleStyleSkillCategories.class);
        SkillSlot.ENUM_MANAGER.registerEnumCls(MOD_ID, EpicFightBattleStyleSkillSlots.class);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(MOD_ID, BattleStyleCategories.class);
        modEventBus.addListener(this::commonSetup);
        AnimationRegistry.RegisterAnimations();
        SoundRegistry.SOUNDS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        EFBSDataKeys.DATA_KEYS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        WeaponTypeInjection.inject();
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
