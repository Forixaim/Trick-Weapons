package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.items.EpicFightBattleStylesTiers;
import net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary.house_lux.OriginArondight;
import net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary.house_lux.OriginExcalibur;
import net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary.imperatrice_lumiere.OriginJoyeuse;
import net.minecraft.world.food.FoodProperties;
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
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicFightBattleStyles.MOD_ID);
	public static final RegistryObject<Item> PLACEHOLDER_CHAKRAM = ITEMS.register("placeholder_chakram", () -> new DaggerItem(new Item.Properties(), Tiers.IRON));
	//public static final RegistryObject<Item> BATTLE_STYLE_BOOK = ITEMS.register("")
	public static final RegistryObject<Item> ORIGIN_EXCALIBUR = ITEMS.register("origin_excalibur", OriginExcalibur::new);
	public static final RegistryObject<Item> ORIGIN_ARONDIGHT = ITEMS.register("origin_arondight", OriginArondight::new);
	public static final RegistryObject<Item> ORIGIN_JOYEUSE = ITEMS.register("origin_joyeuse", OriginJoyeuse::new);
	public static final RegistryObject<Item> BAGUETTE = ITEMS.register("baguette", () -> new LongswordItem(new Item.Properties().durability(0).defaultDurability(0).food(Foods.COOKED_BEEF), EpicFightBattleStylesTiers.BAGUETTE));

}
