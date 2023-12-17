package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.DaggerItem;

public class ItemRegistry
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicFightBattleStyles.MOD_ID);
	public static final RegistryObject<Item> PLACEHOLDER_CHAKRAM = ITEMS.register("placeholder_chakram", () -> new DaggerItem(new Item.Properties(), Tiers.IRON));
	//public static final RegistryObject<Item> BATTLE_STYLE_BOOK = ITEMS.register("")
}
