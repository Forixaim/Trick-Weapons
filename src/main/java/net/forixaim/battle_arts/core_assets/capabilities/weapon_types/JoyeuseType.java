package net.forixaim.battle_arts.core_assets.capabilities.weapon_types;

import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.battle_arts.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.battle_arts.core_assets.colliders.LumiereColliders;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class JoyeuseType extends CoreCapability
{
	private static final JoyeuseType instance = new JoyeuseType();

	private JoyeuseType() {
		init();
	}

	public static JoyeuseType getInstance()
	{
		return instance;
	}

	private void init()
	{
		this.provider
				.addDefaultConditional(COMBO_PROVIDER_REGISTRY.add("sword_shield", InteractionHand.OFF_HAND, CapabilityItem.WeaponCategories.SHIELD, CapabilityItem.Styles.TWO_HAND, true))
				.addDefaultConditional(COMBO_PROVIDER_REGISTRY.add("liech", ProviderConditionalType.SKILL_ACTIVATION, SkillSlots.WEAPON_INNATE, EpicFightSkills.LIECHTENAUER, CapabilityItem.Styles.OCHS, false))
				.addDefaultConditional(COMBO_PROVIDER_REGISTRY.add("default", CapabilityItem.Styles.TWO_HAND, false));
		builder.initialSetup(
				BattleStyleCategories.ORIGIN_JOYEUSE,
				EpicFightSounds.WHOOSH.get(),
				EpicFightSounds.BLADE_HIT.get()
			).collider(LumiereColliders.JOYEUSE)
			.weaponCombinationPredicator(provider.exportCombination())
			.styleProvider(provider.exportStyle());
	}
}
