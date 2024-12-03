package net.forixaim.battle_arts.core_assets.animations;


import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.animations.battle_style.BattleStyleRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;

/**
 * Chakram Animation Module,
 */
@Mod.EventBusSubscriber(modid = EpicFightBattleArts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BattleAnimations
{

	@SubscribeEvent
	public static void registerAnimations(AnimationRegistryEvent event)
	{
		event.getRegistryMap().put(EpicFightBattleArts.MOD_ID, BattleAnimations::Build);
	}

	public static void Build()
	{
		BattleStyleRegistry.Build();
	}
}
