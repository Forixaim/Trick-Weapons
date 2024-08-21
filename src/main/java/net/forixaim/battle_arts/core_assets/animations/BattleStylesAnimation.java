package net.forixaim.battle_arts.core_assets.animations;


import net.forixaim.battle_arts.EpicFightBattleArts;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;

@Mod.EventBusSubscriber(modid = EpicFightBattleArts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class BattleStylesAnimation
{
	@SubscribeEvent
	public void Register(AnimationRegistryEvent Event)
	{
		Event.getRegistryMap().put(EpicFightBattleArts.MOD_ID, this::Build);
	}

	protected abstract void Build();
}
