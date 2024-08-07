package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_attacks;

import com.mojang.datafixers.util.Pair;
import net.forixaim.efm_ex.api.providers.ProviderConditionalType;
import net.forixaim.efm_ex.capabilities.CoreCapability;
import net.forixaim.efm_ex.capabilities.weapon_presets.attacks.MountedAttacks;
import net.forixaim.efm_ex.capabilities.weaponcaps.EXWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.SamuraiStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_types.JoyeuseType;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weapon_types.UchigatanaType;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

public class UchigatanaAttacks
{
	public static void inject()
	{
		UchigatanaType.getInstance().getStyleComboProviderRegistry()
				.add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(
						"samurai_battojutsu",
						ProviderConditionalType.SKILL_EXISTENCE,
						EpicFightBattleStyleSkillSlots.BATTLE_STYLE,
						SkillRegistry.SAMURAI,
						SamuraiStyles.SAMURAI_UCHIGATANA,
						false
				));
		UchigatanaType.getInstance().getStyleComboProviderRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(
			"samurai_battojutsu_sheath",
			EpicFightBattleStyleSkillSlots.BATTLE_STYLE,
			EFBSDataKeys.BATTO_SHEATH.get(),
			SamuraiStyles.SAMURAI_UCHIGATANA_SHEATHED,
			false
		));

		UchigatanaType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(CapabilityItem.Styles.TWO_HAND, default2H));
		UchigatanaType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(SamuraiStyles.SAMURAI_UCHIGATANA, samuraiBattojutsu));
		UchigatanaType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(SamuraiStyles.SAMURAI_UCHIGATANA_SHEATHED, samuraiBattojutsuSheath));
		UchigatanaType.getInstance().getAttackCombinationRegistry().add(CoreCapability.COMBO_PROVIDER_REGISTRY.add(CapabilityItem.Styles.MOUNT, MountedAttacks.mountedSwordAttack));
	}

	public static final Function<Pair<Style, EXWeaponCapability.Builder>, EXWeaponCapability.Builder> default2H = (main) ->
	{
		main.getSecond()
				.newStyleCombo(main.getFirst(), Animations.UCHIGATANA_AUTO1, Animations.UCHIGATANA_AUTO2, Animations.UCHIGATANA_AUTO3, Animations.UCHIGATANA_DASH, Animations.UCHIGATANA_AIR_SLASH)
				.livingMotionModifier(main.getFirst(), LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.SNEAK, Animations.BIPED_WALK_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD)
				.innateSkill(main.getFirst(), (itemstack) -> EpicFightSkills.SWEEPING_EDGE);
		return main.getSecond();
	};

	public static final Function<Pair<Style, EXWeaponCapability.Builder>, EXWeaponCapability.Builder> samuraiBattojutsu = (main) ->
	{
		main.getSecond()
				.newStyleCombo(main.getFirst(), Animations.UCHIGATANA_AUTO1, Animations.UCHIGATANA_AUTO2, Animations.UCHIGATANA_AUTO3, Animations.UCHIGATANA_DASH, Animations.UCHIGATANA_AIR_SLASH)
				.livingMotionModifier(main.getFirst(), LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.SNEAK, Animations.BIPED_WALK_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA)
				.livingMotionModifier(main.getFirst(), LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD)
				.innateSkill(main.getFirst(), (itemstack) -> SkillRegistry.SAMURAI_BATTOJUTSU);
		return main.getSecond();
	};

	public static final Function<Pair<Style, EXWeaponCapability.Builder>, EXWeaponCapability.Builder> samuraiBattojutsuSheath = (main) ->
	{
		main.getSecond()
				.newStyleCombo(main.getFirst(), Animations.UCHIGATANA_SHEATHING_AUTO, Animations.UCHIGATANA_SHEATHING_DASH, Animations.UCHIGATANA_SHEATH_AIR_SLASH)
				.livingMotionModifier(main.getFirst(), LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.CHASE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.SNEAK, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
				.livingMotionModifier(main.getFirst(), LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
				.innateSkill(main.getFirst(), (itemstack) -> SkillRegistry.SAMURAI_BATTOJUTSU);
		return main.getSecond();
	};
}
