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

	public static final RegistryObject<SoundEvent> IMPERATRICE_SWING1 = registerSound("entity.weapon.imperatrice_swing1");
	public static final RegistryObject<SoundEvent> IMPERATRICE_SWING2 = registerSound("entity.weapon.imperatrice_swing2");
	public static final RegistryObject<SoundEvent> IMPERATRICE_SWING3 = registerSound("entity.weapon.imperatrice_swing3");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HIT_S = registerSound("entity.weapon.imperatrice_hit_s");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HIT_M = registerSound("entity.weapon.imperatrice_hit_m");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HIT_L = registerSound("entity.weapon.imperatrice_hit_l");
	public static final RegistryObject<SoundEvent> IMPERATRICE_THRUST_L = registerSound("entity.weapon.imperatrice_thrust_l");
	public static final RegistryObject<SoundEvent> IMPERATRICE_KICK_IMPACT_M = registerSound("entity.weapon.imperatrice_kick_impact_m");
	public static final RegistryObject<SoundEvent> IMPERATRICE_PUNCH_IMPACT_M = registerSound("entity.weapon.imperatrice_punch_impact_m");
	public static final RegistryObject<SoundEvent> IMPERATRICE_ANTI_CHEESE = registerSound("entity.weapon.imperatrice_anti_cheese");

	public static final RegistryObject<SoundEvent> CRITICAL_HIT = registerSound("entity.combat.critical_hit");



	private static RegistryObject<SoundEvent> registerSound(String name) {
		ResourceLocation res = new ResourceLocation(EpicFightBattleStyles.MOD_ID, name);
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
	}
}
