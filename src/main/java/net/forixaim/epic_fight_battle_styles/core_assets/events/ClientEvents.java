package net.forixaim.epic_fight_battle_styles.core_assets.events;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.client.KeyBinds;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents
{
	@SubscribeEvent
	public static void onKeyRegister(RegisterKeyMappingsEvent event)
	{
		event.register(KeyBinds.USE_ART_1);
		event.register(KeyBinds.USE_ART_2);
		event.register(KeyBinds.USE_BURST_ART);
		event.register(KeyBinds.USE_ULTIMATE_ART);

	}


}
