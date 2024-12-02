package net.forixaim.battle_arts.core_assets.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.forixaim.battle_arts.EpicFightBattleArts;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinds
{
	public static final String CATEGORY_EFBS_SKILLS = "key.category.epic_fight_battle_styles.efbs_skills";
	public static final String COMBAT_ART_1 = "key." + EpicFightBattleArts.MOD_ID + ".combat_art_1_use";
	public static final String TAUNT = "key." + EpicFightBattleArts.MOD_ID + ".taunt_use";
	public static final String BURST_ART = "key." + EpicFightBattleArts.MOD_ID + ".burst_art_use";
	public static final String ULTIMATE_ART = "key." + EpicFightBattleArts.MOD_ID + ".ultimate_art_use";

	public static final KeyMapping USE_ART_1 = new KeyMapping(COMBAT_ART_1, KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY_EFBS_SKILLS);
	public static final KeyMapping USE_TAUNT = new KeyMapping(TAUNT, KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, CATEGORY_EFBS_SKILLS);
	public static final KeyMapping USE_BURST_ART = new KeyMapping(BURST_ART, KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, CATEGORY_EFBS_SKILLS);
	public static final KeyMapping USE_ULTIMATE_ART = new KeyMapping(ULTIMATE_ART, KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY_EFBS_SKILLS);

}