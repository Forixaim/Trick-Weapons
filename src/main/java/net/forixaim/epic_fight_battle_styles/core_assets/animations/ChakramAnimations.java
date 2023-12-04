package net.forixaim.epic_fight_battle_styles.core_assets.animations;


import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.colliders.ChakramColliders;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

/**
 * Chakram Animation Module,
 */
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChakramAnimations extends BattleStylesAnimation
{
	// Living Motions for Single Held Chakram.
	public static StaticAnimation SINGLE_CHAKRAM_IDLE;
	public static StaticAnimation SINGLE_CHAKRAM_WALK;
	public static StaticAnimation SINGLE_CHAKRAM_RUN;
	//Combat Motions
	public static StaticAnimation SINGLE_CHAKRAM_AUTO_1;
	public static StaticAnimation SINGLE_CHAKRAM_AUTO_2;
	public static StaticAnimation SINGLE_CHAKRAM_AIR_SLASH;
	public static StaticAnimation SINGLE_CHAKRAM_DASH_ATTACK;
	//Innate Skills
	public static StaticAnimation PRECISION_VERTICAL;

	@Override
	protected void Build()
	{
		HumanoidArmature biped = Armatures.BIPED;
		SINGLE_CHAKRAM_IDLE = new StaticAnimation(true, "chakram/chakram_idle", biped);
		SINGLE_CHAKRAM_WALK = new MovementAnimation(true, "chakram/chakram_walk", biped);
		SINGLE_CHAKRAM_AUTO_1 = new BasicAttackAnimation(0.1f, 0.0f, 0.15f, 0.8f, 1.2f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_auto1", biped)
			.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.05f)
			.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
			.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.9f));
		SINGLE_CHAKRAM_AUTO_2 = new BasicAttackAnimation(0.1f, 0.0f, 0.4f, 0.8f, 5.8f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_auto2", biped)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.05f)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.8f));
		SINGLE_CHAKRAM_AIR_SLASH = new AttackAnimation(0.1f, "chakram/chakram_airslash", biped,
				new AttackAnimation.Phase(0.0f, 0.0f, 0.0f, 0.3f, 0.3f, 0.3f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.3f, 0.0f, 0.0f, 0.6f, 0.6f, 0.6f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.6f, 0.0f, 0.0f, 0.9f, 0.9f, 0.9f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(0.9f, 0.0f, 0.0f, 1.1f, 1.1f, 1.1f, biped.armR, ChakramColliders.CHAKRAM_AIRSLASH),
				new AttackAnimation.Phase(1.1f, 0.0f, 0.0f, 1.3f, 1.3f, 1.3f, biped.toolR, ColliderPreset.DAGGER),
				new AttackAnimation.Phase(0.0f, 0.0f, 0.1f, 0.0f, 2.5f, 2.5f, biped.toolR, ColliderPreset.DAGGER)
		)
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f))
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.1f)
				.addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 1.3f));
		PRECISION_VERTICAL = new AttackAnimation(0.1f, "chakram/precision_vertical", biped,
			new AttackAnimation.Phase(0.0f, 0.1f, 2.7f, 3.4f, 3.5f, 3.5f, biped.rootJoint, ChakramColliders.PRECISION_VERTICAL),
			new AttackAnimation.Phase(3.5f, 0.1f, 3.5f, 3.9f, 3.9f, 3.9f, biped.rootJoint, ChakramColliders.PRECISION_VERTICAL))
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.15f);
		SINGLE_CHAKRAM_DASH_ATTACK = new BasicAttackAnimation(0.1f, 0.0f, 0.15f, 0.8f, 1.2f, ColliderPreset.DAGGER, biped.toolR, "chakram/chakram_dash", biped)
				.addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.05f)
				.addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
				.addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.9f));
	}
}
