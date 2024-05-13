package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts.SimpleCombatArt;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.advanced.Duelist;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.elite.Hero;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.ImperatriceLumiere;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.LuxArmsMaster;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.unique.wom.Atlantean;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.unique.wom.Demon;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillRegistry
{
	//Weapon Innate Skills
	public static Skill PRECISION_VERTICAL;
	public static Skill SLAMMING_HERO;
	//Battle Styles
	public static Skill HERO;
	public static Skill IMPERATRICE_LUMIERE;
	public static Skill DUELIST;
	public static Skill DEMON;
	public static Skill ATLANTEAN;
	public static Skill HOUSE_LUX_ARMS_MASTER;
	public static Skill TEST_COMBAT_ART;

	public static void RegisterSkills()
	{
		SkillManager.register(Hero::new, Hero.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "hero");
		SkillManager.register(Duelist::new, Duelist.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "duelist");
		SkillManager.register(ImperatriceLumiere::new, Hero.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "imperatrice_lumiere");
		SkillManager.register(LuxArmsMaster::new, LuxArmsMaster.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "house_lux_arms_master");
		if (ModList.get().isLoaded("wom"))
		{
			SkillManager.register(Demon::new, Demon.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "demon");
			SkillManager.register(Atlantean::new, Atlantean.CreateBattleStyle(), EpicFightBattleStyles.MOD_ID, "atlantean");
		}
		SkillManager.register(SimpleWeaponInnateSkill::new, SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(
				new ResourceLocation(EpicFightBattleStyles.MOD_ID, "chakram/precision_vertical")
		), EpicFightBattleStyles.MOD_ID, "precision_vertical");
		SkillManager.register(SimpleWeaponInnateSkill::new,  SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(
				new ResourceLocation(EpicFightBattleStyles.MOD_ID, "sword/slamming_hero")), EpicFightBattleStyles.MOD_ID, "slamming_hero");
		SkillManager.register(SimpleCombatArt::new, SimpleCombatArt.createSimpleCombatArt().setAnimations(
				new ResourceLocation(EpicFightBattleStyles.MOD_ID, "sword/slamming_hero"))
		, EpicFightBattleStyles.MOD_ID, "slamming_hero_art");
		SkillManager.register(SimpleCombatArt::new,
				SimpleCombatArt.createSimpleCombatArt()
						.setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/sweeping_edge"))
						.addWeaponCategory(CapabilityItem.WeaponCategories.SWORD)
						.addWeaponCategory(CapabilityItem.WeaponCategories.LONGSWORD)
				, EpicFightBattleStyles.MOD_ID, "test_combat_art");

	}

	@SubscribeEvent
	public static void BuildSkillEvent(SkillBuildEvent OnBuild)
	{
		HOUSE_LUX_ARMS_MASTER = OnBuild.build(EpicFightBattleStyles.MOD_ID, "house_lux_arms_master");
		DUELIST = OnBuild.build(EpicFightBattleStyles.MOD_ID, "duelist");
		HERO = OnBuild.build(EpicFightBattleStyles.MOD_ID, "hero");
		IMPERATRICE_LUMIERE = OnBuild.build(EpicFightBattleStyles.MOD_ID, "imperatrice_lumiere");

		TEST_COMBAT_ART = OnBuild.build(EpicFightBattleStyles.MOD_ID, "test_combat_art");

		if (ModList.get().isLoaded("wom"))
		{
			DEMON = OnBuild.build(EpicFightBattleStyles.MOD_ID, "demon");
			ATLANTEAN = OnBuild.build(EpicFightBattleStyles.MOD_ID, "atlantean");
		}

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
