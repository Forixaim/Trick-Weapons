package net.forixaim.epic_fight_battle_styles.initialization.registry;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts.BurstArt;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts.FlareBurst;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ultimate_arts.ImperatriceUltimateArt;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ultimate_arts.UltimateArt;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.aerials.ImperatriceAerials;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.basic_attack.ImperatriceAttacks;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.common.advanced.Ronin;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.ImperatriceLumiere;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.LuxArmsMaster;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.dodge.Trailblaze;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate.BlazeStingerSkill;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate.SamuraiBattojutsu;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponpassives.JoyeusePassive;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.skill.weaponinnate.ConditionalWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
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
	public static Skill SAMURAI_BATTOJUTSU;
	//Battle Styles
	public static Skill HERO;
	public static Skill IMPERATRICE_LUMIERE;
	public static Skill IMPERATRICE_ATTACK;
	public static Skill IMPERATRICE_AERIALS;
	public static Skill SAMURAI;
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
	//Burst Arts
	public static Skill FLARE_BURST;
	//Ultimate Arts
	public static Skill IMPERATRICE_ULTIMATE;

	@SubscribeEvent
	public static void BuildSkillEvent(SkillBuildEvent OnBuild)
	{
		SkillBuildEvent.ModRegistryWorker registryWorker = OnBuild.createRegistryWorker(EpicFightBattleStyles.MOD_ID);

		HOUSE_LUX_ARMS_MASTER = registryWorker.build("house_lux_arms_master", LuxArmsMaster::new, BattleStyle.CreateBattleStyle());
		SAMURAI = registryWorker.build("samurai", Ronin::new, BattleStyle.CreateBattleStyle().setResource(Skill.Resource.COOLDOWN));

		IMPERATRICE_LUMIERE = registryWorker.build("imperatrice_lumiere", ImperatriceLumiere::new, BattleStyle.CreateBattleStyle());
		IMPERATRICE_ATTACK = registryWorker.build("imperatrice_attack", ImperatriceAttacks::new, ImperatriceAttacks.createImperatriceAttackSet());
		IMPERATRICE_AERIALS = registryWorker.build("imperatrice_aerials", ImperatriceAerials::new, ImperatriceAerials.createImperatriceAerialBuilder());

		FLARE_BURST = registryWorker.build("flare_burst", FlareBurst::new, BurstArt.createBurstArt().setResource(Skill.Resource.COOLDOWN));
		IMPERATRICE_ULTIMATE = registryWorker.build("imperatrice_ultimate", ImperatriceUltimateArt::new, UltimateArt.createUltimateArt().setResource(Skill.Resource.COOLDOWN));

		WeaponInnateSkill BlazeStinger = (WeaponInnateSkill) registryWorker.build(
				"blaze_stinger",
				BlazeStingerSkill::new,
				Skill.createBuilder().setCategory(SkillCategories.WEAPON_INNATE).setResource(Skill.Resource.NONE)
		);
		BlazeStinger.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2))
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05f))
				.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5f))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE, EpicFightDamageType.GUARD_PUNCTURE));
		BLAZE_STINGER = BlazeStinger;

		WeaponInnateSkill samuraiBattojutsu = registryWorker.build("battojutsu", SamuraiBattojutsu::new, ConditionalWeaponInnateSkill.createConditionalWeaponInnateBuilder().setSelector((executer) -> executer.getOriginal().isSprinting() ? 1 : 0).setAnimations(() -> (AttackAnimation)Animations.BATTOJUTSU, () -> (AttackAnimation)Animations.BATTOJUTSU_DASH));
		samuraiBattojutsu.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(6))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
		SAMURAI_BATTOJUTSU = samuraiBattojutsu;

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
