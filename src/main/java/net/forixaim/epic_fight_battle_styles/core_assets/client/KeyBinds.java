package net.forixaim.epic_fight_battle_styles.core_assets.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
import yesman.epicfight.client.events.engine.ControllEngine;

public class KeyBinds
{
	public static final String CATEGORY_EFBS_SKILLS = "key.category.epic_fight_battle_styles.efbs_skills";
	public static final String COMBAT_ART_1 = "key." + EpicFightBattleStyles.MOD_ID + ".combat_art_1_use";
	public static final String COMBAT_ART_2 = "key." + EpicFightBattleStyles.MOD_ID + ".combat_art_2_use";

	public static final KeyMapping USE_ART_1 = new KeyMapping(COMBAT_ART_1, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_Z, CATEGORY_EFBS_SKILLS);
	public static final KeyMapping USE_ART_2 = new KeyMapping(COMBAT_ART_2, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_X, CATEGORY_EFBS_SKILLS);

}
