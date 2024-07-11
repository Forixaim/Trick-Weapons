package net.forixaim.epic_fight_battle_styles.core_assets.colliders;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.OBBCollider;

import static yesman.epicfight.gameasset.ColliderPreset.registerCollider;

public class LumiereColliders
{
	public static final Collider DASH_LIGHT = registerCollider(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "imperatrice_dash_light"), new OBBCollider(3.0D, 0.4D, 1.5D, 0.0D, 1.2D, -1.0D));
}
