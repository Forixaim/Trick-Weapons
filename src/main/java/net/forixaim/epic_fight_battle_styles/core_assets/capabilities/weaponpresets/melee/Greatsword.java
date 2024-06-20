package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HouseLuxAMStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.skillCheck;

public class Greatsword
{
    public static Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
    {
        if (skillCheck(entityPatch, SkillRegistry.HOUSE_LUX_ARMS_MASTER))
            return HouseLuxAMStyles.HLAM_GREATSWORD_EXCALIBUR;
        return CapabilityItem.Styles.TWO_HAND;
    };

    public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> houseLuxAM = (main) ->
    {
        EFBSWeaponCapability.Builder builder = main.getSecond();
        Style style = main.getFirst();
        builder.livingMotionModifier(style, LivingMotions.IDLE, BattleAnimations.HOUSE_LUX_GS_EXCALIBUR_IDLE);
        return builder;
    };

    public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultGS = (main) ->
    {
        EFBSWeaponCapability.Builder builder = main.getSecond();
        Style style = main.getFirst();
        builder.livingMotionModifier(style, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.FLY, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.CREATIVE_FLY, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.CREATIVE_IDLE, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(style, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                .newStyleCombo(style, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
                .innateSkill(style, (itemstack) -> EpicFightSkills.STEEL_WHIRLWIND);
        return builder;
    };
}
