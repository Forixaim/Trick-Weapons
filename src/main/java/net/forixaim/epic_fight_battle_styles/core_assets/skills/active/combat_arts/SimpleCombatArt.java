package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts;

import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class SimpleCombatArt extends ActiveSkill
{
	public static class Builder extends Skill.Builder<SimpleCombatArt> {
		protected ResourceLocation attackAnimation;

		public Builder setCategory(SkillCategory category) {
			this.category = category;
			return this;
		}

		public Builder setActivateType(ActivateType activateType) {
			this.activateType = activateType;
			return this;
		}

		public Builder setResource(Resource resource) {
			this.resource = resource;
			return this;
		}

		public Builder setAnimations(ResourceLocation attackAnimation) {
			this.attackAnimation = attackAnimation;
			return this;
		}
	}

	protected AnimationProvider.AttackAnimationProvider attackAnimation;

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args) {
		executor.playAnimationSynchronized(this.attackAnimation.get(), 0);
		super.executeOnServer(executor, args);
	}

	public SimpleCombatArt(Builder builder) {
		super(builder);
		this.attackAnimation = () -> (AttackAnimation) EpicFightMod.getInstance().animationManager.findAnimationByPath(builder.attackAnimation.toString());

	}

	public static Builder createCombatArt() {
		return (new Builder()).setCategory(EpicFightBattleStyleSkillCategories.COMBAT_ART).setResource(Resource.STAMINA);
	}

	@Override
	public ActiveSkill registerPropertiesToAnimation() {
		AttackAnimation anim = this.attackAnimation.get();

		for (AttackAnimation.Phase phase : anim.phases) {
			phase.addProperties(this.properties.get(0).entrySet());
		}

		return this;
	}

}
