package net.forixaim.epic_fight_battle_styles.core_assets.ui;

import com.google.common.collect.Maps;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.skill.CapabilitySkill;

import java.util.Map;

/**
 * This UI is only shown when a player presses a bound key to open the battle style selection UI.
 * This will additionally be shown automatically when a player logs in for the first time.
 */
public class BattleStyleClassSelection extends Screen
{
    private static final ResourceLocation BATTLE_STYLE_CLASS_SELECTION = new ResourceLocation(EpicFightBattleStyles.MOD_ID, "textures/gui/battle_style_class_selection.png");
    private final Player player;
    private final CapabilitySkill skill;
    //
    private final Map<Skill, Button> slotButtons = Maps.newHashMap();
    protected BattleStyleClassSelection(Player player, CapabilitySkill skill) {
        super(Component.translatable("gui." + EpicFightBattleStyles.MOD_ID + ".battle_style_class_selection"));
        this.player = player;
        this.skill = skill;
    }

    public void setPlayerBattleStyle(Skill skillSelect)
    {
        EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).getSkillCapability().addLearnedSkill(skillSelect);
    }

    @Override
    public void init()
    {

    }
}
