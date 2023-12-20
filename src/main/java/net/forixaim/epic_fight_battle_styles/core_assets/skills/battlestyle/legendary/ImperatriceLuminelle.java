package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary;

import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.BattleStyleCategories;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyles;

import java.util.UUID;

public class ImperatriceLuminelle extends BattleStyles
{
	private float speedBonus;
	private float damageBonus;
	private boolean applied = false;
	private static final UUID EVENT_UUID = UUID.fromString("fceabee5-64fc-40dd-a7a2-4470ed8ff00a");
	private static final CapabilityItem.WeaponCategories[] AVAILABLE_WEAPON_TYPES = {
			CapabilityItem.WeaponCategories.SWORD,
			CapabilityItem.WeaponCategories.LONGSWORD
	};




	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);
		Logger LOGGER = LogUtils.getLogger();
		LOGGER.error("Not Implemented: Imperatrice Luminelle. You Fucking moron. you wasted your skillbook.");
	}
	@Override
	public void onRemoved(SkillContainer container) {
		super.onRemoved(container);

		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID);
	}

	public ImperatriceLuminelle(Skill.Builder<? extends Skill> builder) {
		super(builder);
	}
}
