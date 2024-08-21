package net.forixaim.battle_arts.initialization.registry;

import net.forixaim.battle_arts.core_assets.animations.BattleAnimations;
import net.forixaim.battle_arts.core_assets.animations.BattleStylesAnimation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AnimationRegistry
{
	private static final IEventBus EventBus = FMLJavaModLoadingContext.get().getModEventBus();
	public static void RegisterAnimations()
	{
		//Create instances of classes to build.
		BattleStylesAnimation chakramAnimations = new BattleAnimations();
		EventBus.addListener(chakramAnimations::Register);
		//Place all animation class registers here.


	}
}
