package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class CreativeTabRegistry
{
	private static final IEventBus MainEventBus = FMLJavaModLoadingContext.get().getModEventBus();
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EpicFightBattleStyles.MOD_ID);

	public static final RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("items", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.epic_fight_battle_styles.items"))
			.icon(() -> new ItemStack(ItemRegistry.IRON_SABRE.get()))
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS).hideTitle()
			.withBackgroundLocation(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/battle_arts.png"))
			.displayItems((params, output) -> {
				ItemRegistry.ITEMS.getEntries().forEach(item -> {
					if (item == ItemRegistry.ORIGIN_ARONDIGHT || item == ItemRegistry.ORIGIN_EXCALIBUR || item == ItemRegistry.PLACEHOLDER_CHAKRAM)
						return;
					output.accept(item.get());
				});
			})
			.build());
}
