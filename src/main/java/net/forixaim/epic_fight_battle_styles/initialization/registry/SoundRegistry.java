package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EpicFightBattleStyles.MOD_ID);

	public static final RegistryObject<SoundEvent> IMPERATRICE_SWING = registerSound("entity.weapon.imperatrice_swing1");

	private static RegistryObject<SoundEvent> registerSound(String name) {
		ResourceLocation res = new ResourceLocation(EpicFightBattleStyles.MOD_ID, name);
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
	}
}
