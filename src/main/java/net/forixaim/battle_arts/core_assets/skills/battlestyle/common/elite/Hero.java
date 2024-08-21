package net.forixaim.battle_arts.core_assets.skills.battlestyle.common.elite;

import net.forixaim.battle_arts.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.BattleStyle;
import net.minecraft.nbt.CompoundTag;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

/**
 * A ckass that focuses on the classic sword and shield or even axe combat.
 */
public class Hero extends BattleStyle
{
	private float speedBonus;
	private float speedPenalty;
	private boolean applied = false;
	private static final UUID EVENT_UUID = UUID.fromString("3421b224-d2a4-482b-ad36-8a19b4aa0d25");
	private static final WeaponCategory[] AVAILABLE_WEAPON_TYPES = {
			CapabilityItem.WeaponCategories.SWORD,
			CapabilityItem.WeaponCategories.LONGSWORD,
			CapabilityItem.WeaponCategories.SHIELD,
			CapabilityItem.WeaponCategories.AXE,
			BattleStyleCategories.BATTLE_AXE,
			BattleStyleCategories.HAND_AXE,
	};


	@Override
	public void setParams(CompoundTag parameters)
	{
		super.setParams(parameters);
		this.speedPenalty = parameters.getFloat("speed_penalty");
		this.speedBonus = parameters.getFloat("speed_bonus");
	}

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
			for (WeaponCategory weaponCategory : AVAILABLE_WEAPON_TYPES)
			{
				if (weaponCategory != heldWeaponCategory && !applied)
				{
					event.setAttackSpeed(speedPenalty);
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
