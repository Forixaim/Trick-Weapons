package net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate;

import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.initialization.registry.ItemRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.common.Tags;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.EpicFightEntities;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class BlazeStingerSkill extends WeaponInnateSkill
{

	private static final UUID EVENT_UUID = UUID.fromString("9ed5a11f-c7b2-4679-99db-0a4c8de2f5a3");
	private AnimationProvider<AttackAnimation> sting;

	public BlazeStingerSkill(Builder<? extends Skill> builder)
	{
		super(builder);
		sting = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER;
	}

	@Override
	public void onInitiate(SkillContainer container) {
		super.onInitiate(container);

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_HURT, EVENT_UUID, (event) -> {
			if (event.getDamageSource().getAnimation() == BattleAnimations.IMPERATRICE_SWORD_BLAZE_STINGER) {
				ValueModifier damageModifier = ValueModifier.empty();
				this.getProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, this.properties.get(0)).ifPresent(damageModifier::merge);
				damageModifier.merge(ValueModifier.multiplier(0.8F));
				if (container.getExecuter().getOriginal().getItemInHand(InteractionHand.MAIN_HAND).is(ItemRegistry.ORIGIN_JOYEUSE.get()))
				{
					event.getTarget().setRemainingFireTicks(event.getTarget().getRemainingFireTicks() + 20);
				}
			}
		});
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args) {
		executor.playAnimationSynchronized(sting.get(), 0.0F);
		super.executeOnServer(executor, args);
	}
}
