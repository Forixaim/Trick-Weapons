package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_types;

import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.colliders.LumiereColliders;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class UchigatanaType extends CoreCapability
{
	private static final UchigatanaType instance = new UchigatanaType();

	private UchigatanaType() {
		init();
	}

	public static UchigatanaType getInstance()
	{
		return instance;
	}

	private void init()
	{
		this.provider
				.addDefaultConditional(COMBO_PROVIDER_REGISTRY.add("default", CapabilityItem.Styles.TWO_HAND, false));
		builder.initialSetup(
						CapabilityItem.WeaponCategories.UCHIGATANA,
						EpicFightSounds.WHOOSH.get(),
						EpicFightSounds.BLADE_HIT.get()
				).collider(ColliderPreset.UCHIGATANA)
				.weaponCombinationPredicator(provider.exportCombination())
				.styleProvider(provider.exportStyle());
	}
}
