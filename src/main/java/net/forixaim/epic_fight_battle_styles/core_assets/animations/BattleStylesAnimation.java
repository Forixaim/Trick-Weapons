package net.forixaim.epic_fight_battle_styles.core_assets.animations;


import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;

@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class BattleStylesAnimation
{
	@SubscribeEvent
	public void Register(AnimationRegistryEvent Event)
	{
		Event.getRegistryMap().put(EpicFightBattleStyles.MOD_ID, this::Build);
	}

	protected abstract void Build();
}
