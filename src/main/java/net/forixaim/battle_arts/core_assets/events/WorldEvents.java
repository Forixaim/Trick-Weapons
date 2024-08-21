package net.forixaim.battle_arts.core_assets.events;

import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.entity.bosses.patches.CharlemagnePatch;
import net.forixaim.battle_arts.initialization.registry.EntityRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;

@Mod.EventBusSubscriber(modid = EpicFightBattleArts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents
{
	@SubscribeEvent
	public static void RegisterEntityPatches(EntityPatchRegistryEvent event)
	{
		event.getTypeEntry().put(EntityRegistry.CHARLEMAGNE.get(), entity -> CharlemagnePatch::new);
	}
}
