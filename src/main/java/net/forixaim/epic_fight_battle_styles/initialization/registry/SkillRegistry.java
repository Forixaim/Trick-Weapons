package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts.SimpleCombatArt;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.basic_attack.ImperatriceAttacks;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.advanced.Duelist;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.elite.Hero;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.ImperatriceLumiere;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.LuxArmsMaster;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.unique.wom.Atlantean;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.unique.wom.Demon;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.dodge.Trailblaze;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate.BlazeStingerSkill;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponpassives.JoyeusePassive;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.skill.dodge.StepSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkillRegistry
{
	//Dodge Skills
	public static Skill TRAILBLAZE;
	//Heavy Attacks
	public static Skill PRECISION_VERTICAL;
	public static Skill SLAMMING_HERO;
	public static Skill BLAZE_STINGER;
	//Battle Styles
	public static Skill HERO;
	public static Skill IMPERATRICE_LUMIERE;
	public static Skill IMPERATRICE_ATTACK;
	public static Skill DUELIST;
	public static Skill DEMON;
	public static Skill ATLANTEAN;
	public static Skill HOUSE_LUX_ARMS_MASTER;
	//Weapon Passives
	public static Skill JOYEUSE_PASSIVE;
	//Combat Arts
	public static Skill TEST_COMBAT_ART;
	public static Skill TEST_COMBAT_ART_2;
	public static Skill INFERNAL_WHEEL;

	@SubscribeEvent
	public static void BuildSkillEvent(SkillBuildEvent OnBuild)
	{
		SkillBuildEvent.ModRegistryWorker registryWorker = OnBuild.createRegistryWorker(EpicFightBattleStyles.MOD_ID);

		HOUSE_LUX_ARMS_MASTER = registryWorker.build("house_lux_arms_master", LuxArmsMaster::new, BattleStyle.CreateBattleStyle());

		IMPERATRICE_LUMIERE = registryWorker.build("imperatrice_lumiere", ImperatriceLumiere::new, BattleStyle.CreateBattleStyle());
		IMPERATRICE_ATTACK = registryWorker.build("imperatrice_attack", ImperatriceAttacks::new, ImperatriceAttacks.createImperatriceAttackSet());

		TEST_COMBAT_ART = registryWorker.build("test_combat_art", SimpleCombatArt::new, SimpleCombatArt.createSimpleCombatArt().setAnimations(() -> (AttackAnimation) Animations.SWEEPING_EDGE));
		TEST_COMBAT_ART_2 = registryWorker.build("test_combat_art_2", SimpleCombatArt::new, SimpleCombatArt.createSimpleCombatArt().setAnimations(() -> (AttackAnimation) Animations.THE_GUILLOTINE));

		WeaponInnateSkill BlazeStinger = registryWorker.build(
				"blaze_stinger",
				BlazeStingerSkill::new,
				WeaponInnateSkill.createWeaponInnateBuilder()
		);
		BlazeStinger.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05f))
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE, EpicFightDamageType.GUARD_PUNCTURE));
		BLAZE_STINGER = BlazeStinger;

		TRAILBLAZE = registryWorker.build(
				"trailblaze",
				Trailblaze::new,
				DodgeSkill.createDodgeBuilder().setAnimations(
						() -> BattleAnimations.IMPERATRICE_TRAILBLAZE_FWD,
						() -> BattleAnimations.IMPERATRICE_TRAILBLAZE_BACK,
						() -> BattleAnimations.IMPERATRICE_TRAILBLAZE_LEFT,
						() -> BattleAnimations.IMPERATRICE_TRAILBLAZE_RIGHT
				)
		);

		JOYEUSE_PASSIVE = registryWorker.build("joyeuse_passive", JoyeusePassive::new, Skill.createBuilder().setActivateType(Skill.ActivateType.ONE_SHOT).setCategory(SkillCategories.WEAPON_PASSIVE).setResource(Skill.Resource.NONE));
	}
}
