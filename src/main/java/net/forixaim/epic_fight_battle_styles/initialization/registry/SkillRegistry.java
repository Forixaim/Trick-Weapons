package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.Duelist;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.Hero;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.ImperatriceLuminelle;
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
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillRegistry
{
	//Weapon Innate Skills
	public static Skill PRECISION_VERTICAL;
	public static Skill SLAMMING_HERO;
	//Battle Styles
	public static Skill HERO;
	public static Skill IMPERATRICE_LUMINELLE;
	public static Skill DUELIST;

	public static void RegisterSkills()
	{
		SkillManager.register(Hero::new, Hero.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "hero");
		SkillManager.register(Duelist::new, Duelist.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "duelist");
		SkillManager.register(ImperatriceLuminelle::new, Hero.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "imperatrice_luminelle");
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(
				new ResourceLocation(EpicFightBattleStyles.MOD_ID, "chakram/precision_vertical")
		), EpicFightBattleStyles.MOD_ID, "precision_vertical");
		SkillManager.register(SimpleWeaponInnateSkill::new,  SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(
				new ResourceLocation(EpicFightBattleStyles.MOD_ID, "sword/slamming_hero")), EpicFightBattleStyles.MOD_ID, "slamming_hero");
	}

	@SubscribeEvent
	public static void BuildSkillEvent(SkillBuildEvent OnBuild)
	{
		DUELIST = OnBuild.build(EpicFightBattleStyles.MOD_ID, "duelist");
		HERO = OnBuild.build(EpicFightBattleStyles.MOD_ID, "hero");
		IMPERATRICE_LUMINELLE = OnBuild.build(EpicFightBattleStyles.MOD_ID, "imperatrice_luminelle");
		WeaponInnateSkill PrecisionVertical = OnBuild.build(EpicFightBattleStyles.MOD_ID, "precision_vertical");
		PrecisionVertical
				.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
				.registerPropertiesToAnimation();
		PRECISION_VERTICAL = PrecisionVertical;
		WeaponInnateSkill SlammingHero = OnBuild.build(EpicFightBattleStyles.MOD_ID, "slamming_hero");
		SlammingHero
				.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05f))
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(10f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN);
		SLAMMING_HERO = SlammingHero;
	}
}
