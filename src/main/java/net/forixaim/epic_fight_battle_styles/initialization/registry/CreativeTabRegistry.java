package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

public class CreativeTabRegistry
{
	private static final IEventBus MainEventBus = FMLJavaModLoadingContext.get().getModEventBus();
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EpicFightBattleStyles.MOD_ID);

	public static void InsertItem()
	{
		MainEventBus.addListener(CreativeTabRegistry::SortCreativeTabs);
	}

	private static void SortCreativeTabs(BuildCreativeModeTabContentsEvent Event)
	{

	}
}
