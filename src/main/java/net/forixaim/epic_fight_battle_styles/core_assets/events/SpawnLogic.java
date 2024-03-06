package net.forixaim.epic_fight_battle_styles.core_assets.events;

import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.ui.BattleStyleClassSelection;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

/**
 * These methods are called when a player logs in for the first time.
 */

@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID)
public class SpawnLogic
{
    @SubscribeEvent
    public static void onPlayerLogin(EntityJoinLevelEvent event)
    {
        if (event.getEntity() instanceof Player player)
        {
            if (!player.getTags().contains(EpicFightBattleStyles.MOD_ID + ":first_login"))
            {
                player.getTags().add(EpicFightBattleStyles.MOD_ID + ":first_login");
                LogUtils.getLogger().info("First login detected for player " + player.getName().getString());
                PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            }
        }
    }
}
