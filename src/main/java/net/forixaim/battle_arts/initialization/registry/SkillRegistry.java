package net.forixaim.battle_arts.initialization.registry;

import net.forixaim.battle_arts.EpicFightBattleArts;

import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.advanced.Ronin;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice.NoviceBattleStyles;
import net.forixaim.battle_arts.core_assets.skills.passive.ArrogancePassive;
import net.forixaim.battle_arts.core_assets.skills.weaponinnate.SamuraiBattojutsu;
import net.forixaim.bs_api.battle_arts_skills.battle_style.BattleStyle;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.ConditionalWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@Mod.EventBusSubscriber(modid = EpicFightBattleArts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkillRegistry
{

	public static Skill SAMURAI_BATTOJUTSU;
	public static Skill ARROGANCE;
	//Battle Styles
	public static Skill SAMURAI;

	@SubscribeEvent
	public static void BuildSkillEvent(SkillBuildEvent OnBuild)
	{
		SkillBuildEvent.ModRegistryWorker registryWorker = OnBuild.createRegistryWorker(EpicFightBattleArts.MOD_ID);
		ARROGANCE = registryWorker.build("arrogance", ArrogancePassive::new, PassiveSkill.createPassiveBuilder().setResource(Skill.Resource.NONE));

		NoviceBattleStyles.register(registryWorker);

		SAMURAI = registryWorker.build("ronin", Ronin::new, BattleStyle.CreateBattleStyle().setResource(Skill.Resource.COOLDOWN).setCreativeTab(CreativeTabRegistry.MAIN_ITEMS.get()));

		WeaponInnateSkill samuraiBattojutsu = registryWorker.build("battojutsu", SamuraiBattojutsu::new, ConditionalWeaponInnateSkill.createConditionalWeaponInnateBuilder().setSelector((executer) -> executer.getOriginal().isSprinting() ? 1 : 0).setAnimations(() -> (AttackAnimation)Animations.BATTOJUTSU, () -> (AttackAnimation)Animations.BATTOJUTSU_DASH));
		samuraiBattojutsu.newProperty()
				.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F))
				.addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(6))
				.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
				.addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
				.addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE));
		SAMURAI_BATTOJUTSU = samuraiBattojutsu;


	}
}
