package net.forixaim.epic_fight_battle_styles.balancing;

import yesman.epicfight.world.damagesource.StunType;

/**
 * This class is used to balance the Chakram.
 * It contains values for chakram damage bonus, speed bonus, max strikes, and impact modifier.
 * There are also special variables for stun types.
 * Each set of values is for a specific attack animation.
 */
public class ChakramBalancing
{
	//Auto Attack 1
	public static final double CHAKRAM_AA1_DAMAGE_BONUS = 0.0;
	public static final double CHAKRAM_AA1_SPEED_BONUS = 0.0;
	public static final int CHAKRAM_AA1_MAX_STRIKES = 0;
	public static final double CHAKRAM_AA1_IMPACT_MODIFIER = 0.0;
	public static final StunType CHAKRAM_AA1_STUN_TYPE = StunType.NONE;
	//Auto Attack 2
	public static final double CHAKRAM_AA2_DAMAGE_BONUS = 0.0;
	public static final double CHAKRAM_AA2_SPEED_BONUS = 0.0;
	public static final int CHAKRAM_AA2_MAX_STRIKES = 0;
	public static final double CHAKRAM_AA2_IMPACT_MODIFIER = 0.0;
	public static final StunType CHAKRAM_AA2_STUN_TYPE = StunType.NONE;
	//Dash Attack
	public static final double CHAKRAM_DA_DAMAGE_BONUS = 0.0;
	public static final double CHAKRAM_DA_SPEED_BONUS = 0.0;
	public static final int CHAKRAM_DA_MAX_STRIKES = 0;
	public static final float CHAKRAM_DA_IMPACT_MODIFIER = 0.0f;
	public static final StunType CHAKRAM_DA_STUN_TYPE = StunType.NONE;
	//Aerial Attack
	public static final float CHAKRAM_AERIAL_DAMAGE_BONUS = 0.1f;
	public static final double CHAKRAM_AERIAL_SPEED_BONUS = 0.0;
	public static final int CHAKRAM_AERIAL_MAX_STRIKES = 0;
	public static final double CHAKRAM_AERIAL_IMPACT_MODIFIER = 0.0;
	public static final StunType CHAKRAM_AERIAL_STUN_TYPE = StunType.HOLD;
	//Precision Vertical
	public static final double CHAKRAM_PV_DAMAGE_BONUS = 0.0;
	public static final double CHAKRAM_PV_SPEED_BONUS = 0.0;
	public static final int CHAKRAM_PV_MAX_STRIKES = 0;
	public static final double CHAKRAM_PV_IMPACT_MODIFIER = 0.0;
	public static final StunType CHAKRAM_PV_STUN_TYPE = StunType.NONE;
}
