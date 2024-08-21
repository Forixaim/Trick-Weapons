package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.attacks.LongswordAttacks;
import net.forixaim.efm_ex.capabilities.weapon_presets.attacks.MountedAttacks;
import net.forixaim.battle_arts.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.battle_arts.core_assets.capabilities.weapon_types.JoyeuseType;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillSlots;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class JoyeuseAttacks
{
	public static void injectAttacks()
	{
		JoyeuseType.getInstance().getStyleComboProviderRegistry().add(
				CoreCapability.COMBO_PROVIDER_REGISTRY.add(
						"imperatrice_lumiere_sword",
						ProviderConditionalType.SKILL_EXISTENCE,
						BattleArtsSkillSlots.BATTLE_STYLE,
						SkillRegistry.IMPERATRICE_LUMIERE,
						ImperatriceLumiereStyles.IMPERATRICE_SWORD,
						false
				)
		);
		JoyeuseType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(CapabilityItem.Styles.OCHS, net.forixaim.efm_ex.capabilities.weapon_presets.attacks.LongswordAttacks.LiechtenauerAttackCycle));
		JoyeuseType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(CapabilityItem.Styles.ONE_HAND, net.forixaim.efm_ex.capabilities.weapon_presets.attacks.LongswordAttacks.defaultTwoHandAttackCycle));
		JoyeuseType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(CapabilityItem.Styles.TWO_HAND, LongswordAttacks.defaultTwoHandAttackCycle));
		JoyeuseType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(CapabilityItem.Styles.MOUNT, MountedAttacks.mountedSwordAttack));
		JoyeuseType.getInstance().getAttackCombinationRegistry().add(
				CoreCapability.COMBO_PROVIDER_REGISTRY.add(ImperatriceLumiereStyles.IMPERATRICE_SWORD, ImperatriceWeaponAttacks.imperatriceSword)
		);
	}
}
