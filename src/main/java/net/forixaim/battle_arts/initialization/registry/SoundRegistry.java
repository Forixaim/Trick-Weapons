package net.forixaim.battle_arts.initialization.registry;

import net.forixaim.battle_arts.EpicFightBattleArts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EpicFightBattleArts.MOD_ID);

	public static final RegistryObject<SoundEvent> IMPERATRICE_SWING1 = registerSound("entity.weapon.imperatrice_swing1");
	public static final RegistryObject<SoundEvent> IMPERATRICE_SWING2 = registerSound("entity.weapon.imperatrice_swing2");
	public static final RegistryObject<SoundEvent> IMPERATRICE_SWING3 = registerSound("entity.weapon.imperatrice_swing3");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HEAVY_SWING = registerSound("entity.weapon.imperatrice_heavy_swing");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HIT_S = registerSound("entity.weapon.imperatrice_hit_s");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HIT_M = registerSound("entity.weapon.imperatrice_hit_m");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HIT_L = registerSound("entity.weapon.imperatrice_hit_l");
	public static final RegistryObject<SoundEvent> IMPERATRICE_HIT_FINISHER = registerSound("entity.weapon.imperatrice_hit_finisher");
	public static final RegistryObject<SoundEvent> FATAL_ULTIMATE_HIT = registerSound("entity.weapon.fatal_ultimate");
	public static final RegistryObject<SoundEvent> IMPERATRICE_THRUST_L = registerSound("entity.weapon.imperatrice_thrust_l");
	public static final RegistryObject<SoundEvent> IMPERATRICE_KICK_IMPACT_M = registerSound("entity.weapon.imperatrice_kick_impact_m");
	public static final RegistryObject<SoundEvent> IMPERATRICE_PUNCH_IMPACT_M = registerSound("entity.weapon.imperatrice_punch_impact_m");
	public static final RegistryObject<SoundEvent> IMPERATRICE_ANTI_CHEESE = registerSound("entity.weapon.imperatrice_anti_cheese");
	public static final RegistryObject<SoundEvent> CHEESE = registerSound("entity.combat.nocheese");
	public static final RegistryObject<SoundEvent> CRITICAL_HIT_2 = registerSound("entity.weapon.crit2");
	public static final RegistryObject<SoundEvent> SILENCE = registerSound("util.silence");
	public static final RegistryObject<SoundEvent> SPIKE = registerSound("entity.combat.spike");
	public static final RegistryObject<SoundEvent> FLARE_BURST_AURA = registerSound("entity.weapon.flare_burst_aura");

	public static final RegistryObject<SoundEvent> CRITICAL_HIT = registerSound("entity.combat.critical_hit");
	public static final RegistryObject<SoundEvent> BURST_ART_READY = registerSound("battle_style.burst_art_ready");
	public static final RegistryObject<SoundEvent> ULTIMATE_ART_EXECUTE = registerSound("battle_style.ultimate_art_execute");
	public static final RegistryObject<SoundEvent> IMPERATRICE_FLASH = registerSound("battle_style.imperatrice_lumiere.flash");
	public static final RegistryObject<SoundEvent> IMPERATRICE_SPOT_DODGE = registerSound("entity.weapon.imperatrice_spot_dodge");



	private static RegistryObject<SoundEvent> registerSound(String name) {
		ResourceLocation res = new ResourceLocation(EpicFightBattleArts.MOD_ID, name);
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
	}
}
