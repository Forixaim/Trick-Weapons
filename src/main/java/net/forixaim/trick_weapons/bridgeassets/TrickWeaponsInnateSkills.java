package net.forixaim.trick_weapons.bridgeassets;

import net.forixaim.trick_weapons.TrickWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.SourceTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;
@Mod.EventBusSubscriber(modid=TrickWeapons.MOD_ID, bus=Mod.EventBusSubscriber.Bus.FORGE)
public class TrickWeaponsInnateSkills {
	/**
	 * Precision Vertical is a powerful forward chakram throw that hits twice.
	 */
	public static Skill PRECISION_VERTICAL;
	public static Skill SPINNING_WHIRLWIND;

	public static void RegisterSkills()
	{
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(TrickWeapons.MOD_ID, "chakram/chakram_precision_vertical")), TrickWeapons.MOD_ID, "precision_vertical");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(TrickWeapons.MOD_ID, "chakram/chakram_spinning_whirlwind")), TrickWeapons.MOD_ID, "spinning_whirlwind");
	}

	@SubscribeEvent
	public static void buildSkillEvent(SkillBuildEvent OnBuild)
	{
		WeaponInnateSkill PrecisionVertical = OnBuild.build(TrickWeapons.MOD_ID, "precision_vertical");
		PrecisionVertical.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG )
				.addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.WEAPON_INNATE))
				.registerPropertiesToAnimation();
		PRECISION_VERTICAL = PrecisionVertical;
		WeaponInnateSkill SpinningWhirlwind = OnBuild.build(TrickWeapons.MOD_ID, "spinning_whirlwind");
		SpinningWhirlwind.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG )
				.addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(SourceTags.WEAPON_INNATE))
				.registerPropertiesToAnimation();
		SPINNING_WHIRLWIND = SpinningWhirlwind;
	}
}
