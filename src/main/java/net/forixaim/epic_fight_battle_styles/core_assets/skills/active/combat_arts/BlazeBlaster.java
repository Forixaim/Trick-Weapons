package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;

import java.util.UUID;

public class BlazeBlaster extends CombatArt
{

	private static final UUID EVENT_UUID = UUID.fromString("a4deb3a3-2eb2-4e3b-8204-265e95cc4eaf");
	private AnimationProvider<AttackAnimation> provider;

	public BlazeBlaster(Builder<? extends Skill> builder)
	{
		super(builder);
		provider = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_BLAZE_BLASTER.get();
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);
	}
}
