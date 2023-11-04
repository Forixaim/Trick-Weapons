package net.forixaim.trick_weapons.world.item;

import net.forixaim.trick_weapons.TrickWeapons;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.DaggerItem;

/**
 * The registry of placeholder items
 */
public class TrickWeaponsPlaceholderItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TrickWeapons.MOD_ID);
	/**
	 * Adds a placeholder chakram item mainly to test.
	 */
	public static final RegistryObject<Item> wIron_Chakram = ITEMS.register("iron_chakram_placeholder", () -> new DaggerItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), Tiers.IRON));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}