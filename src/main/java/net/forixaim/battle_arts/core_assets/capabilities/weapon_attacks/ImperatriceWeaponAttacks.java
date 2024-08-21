package net.forixaim.battle_arts.core_assets.capabilities.weapon_attacks;

import com.mojang.datafixers.util.Pair;
import net.forixaim.efm_ex.capabilities.weaponcaps.EXWeaponCapability;
import net.forixaim.battle_arts.core_assets.animations.BattleAnimations;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

public class ImperatriceWeaponAttacks
{
	public static Function<Pair<Style, EXWeaponCapability.Builder>, EXWeaponCapability.Builder> imperatriceSword = (main) ->
	{
		EXWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		builder.livingMotionModifier(style, LivingMotions.IDLE, BattleAnimations.IMPERATRICE_SWORD_EN_GARDE);
		builder.livingMotionModifier(style, LivingMotions.WALK, BattleAnimations.IMPERATRICE_SWORD_WALK);
		builder.livingMotionModifier(style, LivingMotions.RUN, BattleAnimations.IMPERATRICE_SWORD_RUN);
		builder.livingMotionModifier(style, LivingMotions.KNEEL, BattleAnimations.IMPERATRICE_SWORD_CROUCH);
		builder.livingMotionModifier(style, LivingMotions.SNEAK, BattleAnimations.IMPERATRICE_SWORD_CROUCH_WALK);
		builder.livingMotionModifier(style, LivingMotions.JUMP, BattleAnimations.IMPERATRICE_SWORD_JUMP);
		builder.livingMotionModifier(style, LivingMotions.BLOCK, BattleAnimations.IMPERATRICE_GUARD);
		builder.newStyleCombo(style,
				BattleAnimations.IMPERATRICE_SWORD_JAB1,
				BattleAnimations.IMPERATRICE_SWORD_JAB2,
				BattleAnimations.IMPERATRICE_SWORD_JAB3,
				BattleAnimations.IMPERATRICE_SWORD_DOWN_SMASH,
				BattleAnimations.IMPERATRICE_SWORD_FLARE_BURST,
				BattleAnimations.IMPERATRICE_SWORD_DASH_ATTACK,
				BattleAnimations.IMPERATRICE_SWORD_FORWARD_AERIAL
		);
		builder.innateSkill(style, (itemstack) -> SkillRegistry.BLAZE_STINGER);
		return builder;
	};
}
