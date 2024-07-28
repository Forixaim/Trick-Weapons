package net.forixaim.epic_fight_battle_styles.core_assets.colliders;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

import static yesman.epicfight.gameasset.ColliderPreset.registerCollider;

public class LumiereColliders
{
	private static ResourceLocation reg(String name)
	{
		return new ResourceLocation(EpicFightBattleStyles.MOD_ID, name);
	}
	public static final Collider JOYEUSE = registerCollider(reg("joyeuse"), new MultiOBBCollider(15,
			0.3D, 0.3D, 1.2D, 0.0D, 0D, -1D
	));

	public static final Collider IMPERATRICE_DOWN_SMASH = registerCollider(reg("imperatrice_down_smash"), new OBBCollider(3, 0.7, 3, 0, 0, 0));
	public static final Collider IMPERATRICE_FLARE_BURST = registerCollider(reg("imperatrice_flare_burst"), new OBBCollider(4, 4, 4, 0, 0, 0));

	public static final Collider IMPERATRICE_ULTIMATE_TRY = registerCollider(reg("imperatrice_ultimate_try"), new OBBCollider(4, 2, 4, 0, 0, -4));
	public static final Collider IMPERATRICE_FLARE_BLADE_CLEAVE = registerCollider(reg("imperatrice_flare_blade_cleave"), new OBBCollider(5, 10, 20, 0, 0, -20));

	public static final Collider RTILT_THIGH = registerCollider(reg("rtilt_t"), new MultiOBBCollider(7,
			0.35D, 0.35D, 0.35D, 0.0D, 0D, -0D
	));
	public static final Collider RTILT_LEG = registerCollider(reg("rtilt_l"), new MultiOBBCollider(7,
			0.35D, 0.8D, 0.35D, 0.0D, 0.4D, -0D
	));
	public static final Collider DASH_LIGHT = registerCollider(reg("imperatrice_dash_light"),
			new OBBCollider(3.0D, 0.4D, 1.5D, 0.0D, 1.2D, -1.0D));
	public static final Collider FORWARD_TILT = registerCollider(reg("imperatrice_ftilt"),
			new OBBCollider(4.0D, 0.4D, 2.0D, 0.0D, 0D, -1.0D));
}
