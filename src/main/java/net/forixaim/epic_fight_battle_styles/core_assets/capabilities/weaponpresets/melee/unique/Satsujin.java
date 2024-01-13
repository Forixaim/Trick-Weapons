package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.unique;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.WoMUniqueStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.skill.weaponpassive.SatsujinPassive;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

public class Satsujin
{
	private static final Logger LOGGER = LogUtils.getLogger();
	public Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
	{
		if (entityPatch instanceof PlayerPatch<?> playerPatch && ModList.get().isLoaded("wom"))
		{
			if (playerPatch.getSkill(SkillRegistry.DEMON) != null)
			{
				//LOGGER.debug("Demon skill detected.");
				if (playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().hasData(SatsujinPassive.SHEATH) && playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(SatsujinPassive.SHEATH))
				{
					return WoMUniqueStyles.DEMON_SHEATH;
				}
				return WoMUniqueStyles.DEMON;
			}
		}
		return CapabilityItem.Styles.TWO_HAND;
	};

	public Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> demonAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		if (ModList.get().isLoaded("wom"))
		{
			builder.livingMotionModifier(style, LivingMotions.IDLE, WOMAnimations.KATANA_IDLE)
					.livingMotionModifier(style, LivingMotions.KNEEL, WOMAnimations.KATANA_IDLE)
					.livingMotionModifier(style, LivingMotions.WALK, WOMAnimations.KATANA_WALK)
					.livingMotionModifier(style, LivingMotions.CHASE, WOMAnimations.KATANA_RUN)
					.livingMotionModifier(style, LivingMotions.RUN, WOMAnimations.KATANA_RUN)
					.livingMotionModifier(style, LivingMotions.SNEAK, WOMAnimations.KATANA_IDLE)
					.livingMotionModifier(style, LivingMotions.SWIM, WOMAnimations.KATANA_IDLE)
					.livingMotionModifier(style, LivingMotions.FLOAT, WOMAnimations.KATANA_IDLE)
					.livingMotionModifier(style, LivingMotions.FALL, WOMAnimations.KATANA_IDLE)
					.livingMotionModifier(style, LivingMotions.BLOCK, WOMAnimations.KATANA_GUARD);
			builder.newStyleCombo(style,
					WOMAnimations.KATANA_AUTO_1,
					WOMAnimations.KATANA_AUTO_2,
					WOMAnimations.KATANA_AUTO_3,
					WOMAnimations.KATANA_DASH,
					WOMAnimations.HERRSCHER_AUSROTTUNG);
			builder.innateSkill(style, (itemstack) -> WOMSkills.SAKURA_STATE);
		}

		return builder;
	};

	public Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> demonSheathAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		if (ModList.get().isLoaded("wom"))
		{
			builder.livingMotionModifier(style, LivingMotions.IDLE, WOMAnimations.KATANA_SHEATHED_IDLE)
					.livingMotionModifier(style, LivingMotions.KNEEL,  WOMAnimations.KATANA_SHEATHED_IDLE)
					.livingMotionModifier(style, LivingMotions.WALK,  WOMAnimations.KATANA_SHEATHED_IDLE)
					.livingMotionModifier(style, LivingMotions.CHASE,  WOMAnimations.KATANA_SHEATHED_RUN)
					.livingMotionModifier(style, LivingMotions.RUN,  WOMAnimations.KATANA_SHEATHED_RUN)
					.livingMotionModifier(style, LivingMotions.SNEAK,  WOMAnimations.KATANA_SHEATHED_IDLE)
					.livingMotionModifier(style, LivingMotions.SWIM,  WOMAnimations.KATANA_SHEATHED_IDLE)
					.livingMotionModifier(style, LivingMotions.FLOAT,  WOMAnimations.KATANA_SHEATHED_IDLE)
					.livingMotionModifier(style, LivingMotions.FALL, WOMAnimations.KATANA_SHEATHED_IDLE);
			builder.newStyleCombo(style,
					WOMAnimations.KATANA_SHEATHED_AUTO_1,
					WOMAnimations.KATANA_SHEATHED_AUTO_2,
					WOMAnimations.KATANA_SHEATHED_AUTO_3,
					WOMAnimations.KATANA_SHEATHED_DASH,
					WOMAnimations.HERRSCHER_AUSROTTUNG
			);
		}

		builder.innateSkill(style, (itemstack) -> WOMSkills.SAKURA_STATE);
		return builder;
	};

	public Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultTwoHandAttackCycle = (main) ->
	{
		EFBSWeaponCapability.Builder builder = main.getSecond();
		Style style = main.getFirst();
		if (ModList.get().isLoaded("wom"))
		{
			builder.livingMotionModifier(style, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
					.livingMotionModifier(style, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI);
			builder.livingMotionModifier(style, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD);
			builder.newStyleCombo(style,
					Animations.LONGSWORD_AUTO1,
					Animations.LONGSWORD_AUTO2,
					Animations.LONGSWORD_AUTO3,
					Animations.LONGSWORD_DASH,
					Animations.SWORD_AIR_SLASH
			);
			builder.innateSkill(style, (itemstack) -> EpicFightSkills.SHARP_STAB);
		}


		return builder;
	};
}
