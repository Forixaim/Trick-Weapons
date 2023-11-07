package net.forixaim.trick_weapons;

import com.mojang.logging.LogUtils;
import net.forixaim.trick_weapons.bridgeassets.TrickWeaponsAnimations;
import net.forixaim.trick_weapons.bridgeassets.TrickWeaponsCapabilities;
import net.forixaim.trick_weapons.bridgeassets.TrickWeaponsInnateSkills;
import net.forixaim.trick_weapons.bridgeassets.TrickWeaponsStyles;
import net.forixaim.trick_weapons.world.item.TrickWeaponsPlaceholderItems;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("trick_weapons")
public class TrickWeapons {

    // Directly reference a slf4j logger
    public static final String MOD_ID = "trick_weapons";
    private static final Logger LOGGER = LogUtils.getLogger();

    public IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public TrickWeapons() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        TrickWeaponsPlaceholderItems.register(eventBus);
        TrickWeaponsStyles.ENUM_MANAGER.loadPreemptive(TrickWeaponsStyles.class);
        TrickWeaponsCapabilities.TrickWeaponCategories.ENUM_MANAGER.loadPreemptive(TrickWeaponsCapabilities.TrickWeaponCategories.class);
        eventBus.addListener(TrickWeaponsAnimations::RegisterAnimations);
        eventBus.addListener(TrickWeaponsCapabilities::register);
        TrickWeaponsInnateSkills.RegisterSkills();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("trick_weaponstrick_weapons", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event) {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
