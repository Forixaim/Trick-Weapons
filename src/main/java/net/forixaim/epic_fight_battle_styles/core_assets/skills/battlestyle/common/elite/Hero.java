package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.elite;

import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyles;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

/**
 * A ckass that focuses on the classic sword and shield or even axe combat.
 */
public class Hero extends BattleStyles
{
	private float speedBonus;
	private float damageBonus;
	private boolean applied = false;
	private static final UUID EVENT_UUID = UUID.fromString("3421b224-d2a4-482b-ad36-8a19b4aa0d25");
	private static final CapabilityItem.WeaponCategories[] AVAILABLE_WEAPON_TYPES = {
			CapabilityItem.WeaponCategories.AXE,
			CapabilityItem.WeaponCategories.LONGSWORD,
			CapabilityItem.WeaponCategories.SWORD,
			CapabilityItem.WeaponCategories.SHIELD
	};

	private static final BattleStyleCategories[] EXTRA_WEAPON_TYPES = {
			BattleStyleCategories.BATTLE_AXE,
			BattleStyleCategories.HAND_AXE
	};

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);
		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID, (event) -> {
			WeaponCategory heldWeaponCategory = event.getItemCapability().getWeaponCategory();
			for (WeaponCategory weaponCategory : AVAILABLE_WEAPON_TYPES)
			{
				if (weaponCategory == heldWeaponCategory && !applied)
				{
					float attackSpeed = event.getAttackSpeed();
					event.setAttackSpeed(attackSpeed * (1.0F + this.speedBonus * 0.01F));
					applied = true;
					break;
				}
			}
			for (WeaponCategory weaponCategory : EXTRA_WEAPON_TYPES)
			{
				if (weaponCategory == heldWeaponCategory && !applied)
				{
					float attackSpeed = event.getAttackSpeed();
					event.setAttackSpeed(attackSpeed * (1.0F + this.speedBonus * 0.01F));
					applied = true;
					break;
				}
			}
			for (WeaponCategory weaponCategory : AVAILABLE_WEAPON_TYPES)
			{
				if (weaponCategory != heldWeaponCategory && !applied)
				{
					event.setAttackSpeed(0.1f);
					applied = true;
					break;
				}
			}
			for (WeaponCategory weaponCategory : EXTRA_WEAPON_TYPES)
			{
				if (weaponCategory != heldWeaponCategory && !applied)
				{
					event.setAttackSpeed(0.1f);
					applied = true;
					break;
				}
			}
			applied = false;
		});
	}
	@Override
	public void onRemoved(SkillContainer container) {
		super.onRemoved(container);

		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID);

	}

	public Hero(Builder<? extends Skill> builder) {
		super(builder);
	}
}
