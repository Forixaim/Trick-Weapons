package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;


import com.mojang.datafixers.util.Pair;

import net.minecraft.sounds.SoundEvent;

import org.jetbrains.annotations.NotNull;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.*;

import java.util.function.Function;

/**
 * A custom implementation of the original WeaponCapability class with a focus on modularity with a conditional passive skill provider.
 */
public class EFBSWeaponCapability extends WeaponCapability
{
	protected final Function<LivingEntityPatch<?>, Skill> passiveSkillProvider;
	protected EFBSWeaponCapability(CapabilityItem.Builder builder)
	{
		super(builder);

		EFBSWeaponCapability.Builder efbsBuilder = (EFBSWeaponCapability.Builder) builder;
		this.passiveSkillProvider = efbsBuilder.passiveSkillProvider;
	}

	public static EFBSWeaponCapability.Builder builder() {
		return new EFBSWeaponCapability.Builder();
	}
	/**
	 * The modified builder that allows for easier weapon style creation and conditional passive skill providers.
	 */
	public static class Builder extends WeaponCapability.Builder
	{
		Function<LivingEntityPatch<?>, Skill> passiveSkillProvider;

		protected Builder()
		{
			super();
		}

		public EFBSWeaponCapability.Builder initialSetup(WeaponCategory category, Skill passiveSkill, SoundEvent swingSound, SoundEvent hitSound, @NotNull Collider collider)
		{
			this.category(category);
			this.passiveSkill(passiveSkill);
			this.swingSound(swingSound);
			this.hitSound(hitSound);
			this.collider(collider);
			return this;
		}

		public EFBSWeaponCapability.Builder initialSetup(WeaponCategory category, SoundEvent swingSound, SoundEvent hitSound)
		{
			this.category(category);
			this.swingSound(swingSound);
			this.hitSound(hitSound);
			return this;
		}

		public EFBSWeaponCapability.Builder redirectedProvider(Function<LivingEntityPatch<?>, Style> styleProvider)
		{
			this.styleProvider(styleProvider);
			return this;
		}

		public EFBSWeaponCapability.Builder redirectedCategory(WeaponCategory category)
		{
			this.category(category);
			return this;
		}

		public EFBSWeaponCapability.Builder redirectedPassiveSkill(Skill passiveSkill)
		{
			this.passiveSkill(passiveSkill);
			return this;
		}

		public EFBSWeaponCapability.Builder redirectedSwingSound(SoundEvent swingSound)
		{
			this.swingSound(swingSound);
			return this;
		}

		public EFBSWeaponCapability.Builder redirectedHitSound(SoundEvent hitSound)
		{
			this.hitSound(hitSound);
			return this;
		}

		public EFBSWeaponCapability.Builder redirectedCollider(@NotNull Collider collider)
		{
			this.collider(collider);
			return this;
		}

		public Builder createStyleCategory(Style style, Function<Pair<Style, Builder>, Builder> weaponCombo)
		{
			return weaponCombo.apply(new Pair<>(style, this));

		}

		public Builder offHandUse(Boolean use)
		{
			this.canBePlacedOffhand(use);
			return this;
		}

		public Builder redirectedPredicator(Function<LivingEntityPatch<?>, Boolean> predicator)
		{
			this.weaponCombinationPredicator(predicator);
			return this;
		}
	}
}
