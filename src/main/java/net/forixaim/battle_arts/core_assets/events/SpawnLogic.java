package net.forixaim.battle_arts.core_assets.events;

import com.mojang.logging.LogUtils;
import net.forixaim.battle_arts.EpicFightBattleArts;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

/**
 * These methods are called when a player logs in for the first time.
 */

@Mod.EventBusSubscriber(modid = EpicFightBattleArts.MOD_ID)
public class SpawnLogic
{
    @SubscribeEvent
    public static void onPlayerLogin(EntityJoinLevelEvent event)
    {
        if (event.getEntity() instanceof Player player)
        {
            if (!player.getTags().contains(EpicFightBattleArts.MOD_ID + ":first_login"))
            {
                player.getTags().add(EpicFightBattleArts.MOD_ID + ":first_login");
                LogUtils.getLogger().info("First login detected for player " + player.getName().getString());
                PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            }
        }
    }
}
