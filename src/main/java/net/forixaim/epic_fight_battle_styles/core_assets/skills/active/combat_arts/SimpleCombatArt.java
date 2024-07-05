package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.ArrayList;
import java.util.List;

public class SimpleCombatArt extends CombatArt
{
	public static class Builder extends Skill.Builder<SimpleCombatArt> {
		protected AttackAnimationProvider attackAnimation;
		protected List<WeaponCategory> allowedWeapons = Lists.newArrayList();

		public Builder addWeaponCategory(WeaponCategory category)
		{
			allowedWeapons.add(category);
			return this;
		}

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

		public Builder setAnimations(AttackAnimationProvider provider) {
			this.attackAnimation = provider;
			return this;
		}
	}

	public static Builder createSimpleCombatArt() {
		return (new Builder()).setCategory(EpicFightBattleStyleSkillCategories.COMBAT_ART).setResource(Resource.COOLDOWN);
	}

	protected AttackAnimationProvider attackAnimation;

	public SimpleCombatArt(Builder builder) {
		super(builder);
		this.allowedWeapons = builder.allowedWeapons;
		this.attackAnimation = builder.attackAnimation;
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args)
	{
		executer.playAnimationSynchronized(this.attackAnimation.get(), 0);
		super.executeOnServer(executer, args);
	}

	@Override
	public CombatArt registerPropertiesToAnimation() {
		AttackAnimation anim = this.attackAnimation.get();
		if (!this.properties.isEmpty())
		{
			for (AttackAnimation.Phase phase : anim.phases) {
				phase.addProperties(this.properties.get(0).entrySet());
			}
		}

		return this;
	}
}
