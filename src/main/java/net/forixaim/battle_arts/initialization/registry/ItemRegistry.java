package net.forixaim.battle_arts.initialization.registry;

import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.items.EpicFightBattleStylesTiers;
import net.forixaim.battle_arts.core_assets.items.weapons.legendary.house_lux.OriginArondight;
import net.forixaim.battle_arts.core_assets.items.weapons.legendary.house_lux.OriginExcalibur;
import net.forixaim.battle_arts.core_assets.items.weapons.legendary.imperatrice_lumiere.OriginJoyeuse;
import net.forixaim.battle_arts.core_assets.items.weapons.normal.SabreItem;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.DaggerItem;
import yesman.epicfight.world.item.LongswordItem;

public class ItemRegistry
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicFightBattleArts.MOD_ID);
	public static final RegistryObject<Item> PLACEHOLDER_CHAKRAM = ITEMS.register("placeholder_chakram", () -> new DaggerItem(new Item.Properties(), Tiers.IRON));
	//public static final RegistryObject<Item> BATTLE_STYLE_BOOK = ITEMS.register("")
	public static final RegistryObject<Item> ORIGIN_EXCALIBUR = ITEMS.register("origin_excalibur", OriginExcalibur::new);
	public static final RegistryObject<Item> ORIGIN_ARONDIGHT = ITEMS.register("origin_arondight", OriginArondight::new);
	public static final RegistryObject<Item> ORIGIN_JOYEUSE = ITEMS.register("origin_joyeuse", OriginJoyeuse::new);
	public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette", () -> new LongswordItem(new Item.Properties().durability(0).defaultDurability(0).food(Foods.COOKED_BEEF), EpicFightBattleStylesTiers.BAGUETTE));

	//Sabres
	public static final RegistryObject<Item> IRON_SABRE = ITEMS.register("iron_sabre", () -> new SabreItem(Tiers.IRON, new Item.Properties()));
	public static final RegistryObject<Item> GOLDEN_SABRE = ITEMS.register("golden_sabre", () -> new SabreItem(Tiers.GOLD, new Item.Properties()));
	public static final RegistryObject<Item> DIAMOND_SABRE = ITEMS.register("diamond_sabre", () -> new SabreItem(Tiers.DIAMOND, new Item.Properties()));
	public static final RegistryObject<Item> NETHERITE_SABRE = ITEMS.register("netherite_sabre", () -> new SabreItem(Tiers.NETHERITE, new Item.Properties().fireResistant()));

	//WoM Compat
	public static final RegistryObject<Item> BROKEN_SOLAR = ITEMS.register("broken_solar", () -> new Item(new Item.Properties().stacksTo(1).fireResistant()));

}
