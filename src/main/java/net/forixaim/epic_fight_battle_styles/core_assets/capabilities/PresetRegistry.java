package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import net.forixaim.efm_ex.EpicFightEXCapability;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_types.JoyeuseType;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_types.UchigatanaType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.function.Function;

//This is where all the weapon capability presets are implemented
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PresetRegistry
{
	public static Function<Item, CapabilityItem.Builder> JOYEUSE = (item ->
			JoyeuseType.getInstance().export());

	public static Function<Item, CapabilityItem.Builder> UCHIGATANA = (item ->
			UchigatanaType.getInstance().export());

	@SubscribeEvent
	public static void Register(WeaponCapabilityPresetRegistryEvent Event)
	{
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "joyeuse"), JOYEUSE);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightEXCapability.MODID, "uchigatana"), UCHIGATANA);
	}
}
