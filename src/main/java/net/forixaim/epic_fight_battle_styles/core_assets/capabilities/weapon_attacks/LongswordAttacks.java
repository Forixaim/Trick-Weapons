package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_attacks;

import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.LongswordType;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;

public class LongswordAttacks
{
	public static void injectAttacks()
	{
		LongswordType.getInstance().getStyleComboProviderRegistry().add(
				CoreCapability.COMBO_PROVIDER_REGISTRY.add(
						"imperatrice_lumiere_sword",
						ProviderConditionalType.SKILL_EXISTENCE,
						EpicFightBattleStyleSkillSlots.BATTLE_STYLE,
						SkillRegistry.IMPERATRICE_LUMIERE,
						ImperatriceLumiereStyles.IMPERATRICE_SWORD,
						false
				)
		);

		LongswordType.getInstance().getAttackCombinationRegistry().add(
				CoreCapability.COMBO_PROVIDER_REGISTRY.add(ImperatriceLumiereStyles.IMPERATRICE_SWORD, ImperatriceWeaponAttacks.imperatriceSword)
		);
	}
}
