package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.offHandItem;
import static net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions.skillCheck;

public class Tachi
{
    public static Function<LivingEntityPatch<?>, Style> styleProvider = (entityPatch) ->
    {
        if (skillCheck(entityPatch, SkillRegistry.IMPERATRICE_LUMIERE))
        {
            return ImperatriceLumiereStyles.IMPERATRICE_SWORD;
        }
        else
        {
            return CapabilityItem.Styles.TWO_HAND;
        }
    };

    public static Function<Pair<Style, EFBSWeaponCapability.Builder>, EFBSWeaponCapability.Builder> defaultTachiAttack = (main) ->
    {
        EFBSWeaponCapability.Builder builder = main.getSecond();
        Style style = main.getFirst();

        builder.livingMotionModifier(style, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
            .livingMotionModifier(style, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
        builder.innateSkill(style, (itemstack) -> EpicFightSkills.RUSHING_TEMPO);
        builder.newStyleCombo(style, Animations.TACHI_AUTO1, Animations.TACHI_AUTO2, Animations.TACHI_AUTO3, Animations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH);

        return builder;
    };
}
