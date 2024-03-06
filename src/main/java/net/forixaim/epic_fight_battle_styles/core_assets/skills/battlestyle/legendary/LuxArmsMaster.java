package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary;

import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import org.slf4j.Logger;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class LuxArmsMaster extends BattleStyle
{
    private static final UUID EVENT_UUID = UUID.fromString("0ca1d98e-48fb-4c9b-9b4f-cfa277304d96");

    public LuxArmsMaster(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container)
    {
        super.onInitiate(container);
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.error("Not Implemented: HLAM. You Fucking moron. you wasted your skillbook.");
    }
    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);

        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID);
    }
}
