package net.forixaim.battle_arts.core_assets.capabilities;

import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.RecruitWieldStyles;
import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.SquireWieldStyles;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.recruit.RecruitMoveSets;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.movesets.novice.squire.SquireMoveSet;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.novice.RecruitProviders;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks.providers.novice.SquireProviders;
import net.forixaim.efm_ex.api.events.ExCapWeaponRegistryEvent;
import net.forixaim.efm_ex.capabilities.weapon_presets.ExCapWeapons;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicFightBattleArts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeaponTypeInjection
{

	@SubscribeEvent
	public static void inject(ExCapWeaponRegistryEvent event)
	{
		event.addProvider(ExCapWeapons.LONGSWORD, SquireProviders.SQUIRE_SWORD_STYLE_CHECK);
		event.addProvider(ExCapWeapons.SWORD, SquireProviders.SQUIRE_SWORD_STYLE_CHECK);
		event.addProvider(ExCapWeapons.TACHI, SquireProviders.SQUIRE_SWORD_STYLE_CHECK);

		event.addMoveset(ExCapWeapons.LONGSWORD, SquireWieldStyles.SQUIRE_SWORD, SquireMoveSet.SquireSwordMS);
		event.addMoveset(ExCapWeapons.SWORD, SquireWieldStyles.SQUIRE_SWORD, SquireMoveSet.SquireSwordMS);
		event.addMoveset(ExCapWeapons.TACHI, SquireWieldStyles.SQUIRE_SWORD, SquireMoveSet.SquireSwordMS);

		event.addProvider(ExCapWeapons.SPEAR, RecruitProviders.RECRUIT_SPEAR_CHECK);
		event.addMoveset(ExCapWeapons.SPEAR, RecruitWieldStyles.RECRUIT_SPEAR, RecruitMoveSets.RECRUIT_MOVESET);
	}

}
