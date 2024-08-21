package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillSlots;
import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.types.LongswordType;
import net.forixaim.battle_arts.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;

public class LongswordAttacks
{
	public static void injectAttacks()
	{
		LongswordType.getInstance().getStyleComboProviderRegistry().add(
				CoreCapability.COMBO_PROVIDER_REGISTRY.add(
						"imperatrice_lumiere_sword",
						ProviderConditionalType.SKILL_EXISTENCE,
						BattleArtsSkillSlots.BATTLE_STYLE,
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
