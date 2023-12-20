package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common;

import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.BattleStyleCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyles;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class Duelist extends BattleStyles
{
	private float speedBonus;
	private float damageBonus;
	private boolean applied = false;
	private static final UUID EVENT_UUID = UUID.fromString("af0bfde5-2535-4ef6-b709-9277b17d2a1a");
	private static final CapabilityItem.WeaponCategories[] AVAILABLE_WEAPON_TYPES = {
			CapabilityItem.WeaponCategories.LONGSWORD,
			CapabilityItem.WeaponCategories.SWORD
	};

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);
	}
	@Override
	public void onRemoved(SkillContainer container) {
		super.onRemoved(container);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID);

	}

	public Duelist(Skill.Builder<? extends Skill> builder) {
		super(builder);
	}
}
