package net.forixaim.epic_fight_battle_styles.core_assets.events;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.server.commands.BattleStyleCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID)
public class WorldEvents
{
    @SubscribeEvent
    public static void onCommandRegistry(RegisterCommandsEvent event)
    {
        BattleStyleCommand.register(event.getDispatcher());
    }
}
