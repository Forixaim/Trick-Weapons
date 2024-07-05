package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditional;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.StyleComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HouseLuxAMStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Greatsword
{
    public static List<ProviderConditional> styleProviderRegistry = new ArrayList<>();
    public static List<ProviderConditional> comboProviderRegistry = new ArrayList<>();

    private static final StyleComboProvider STYLE_COMBO_PROVIDER = new StyleComboProvider()
            .addConditional(new ProviderConditional(ProviderConditionalType.SKILL_EXISTENCE, HouseLuxAMStyles.HLAM_GREATSWORD_EXCALIBUR, SkillRegistry.HOUSE_LUX_ARMS_MASTER, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null));

    private static final ComboProvider comboProvider = new ComboProvider();

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

    private static final EFBSWeaponCapability.Builder greatswordBuilder = EFBSWeaponCapability.builder()
            .redirectedCategory(CapabilityItem.WeaponCategories.GREATSWORD)
            .redirectedCollider(ColliderPreset.GREATSWORD)
            .redirectedSwingSound(EpicFightSounds.WHOOSH_BIG.get())
            .redirectedProvider(Greatsword.STYLE_COMBO_PROVIDER
                    .addDefaultConditional(new ProviderConditional(ProviderConditionalType.DEFAULT, CapabilityItem.Styles.TWO_HAND, false))
                    .exportStyle()
            )
            .createStyleCategory(HouseLuxAMStyles.HLAM_GREATSWORD_EXCALIBUR, Greatsword.houseLuxAM)
            .createStyleCategory(CapabilityItem.Styles.TWO_HAND, Greatsword.defaultGS)
            .offHandUse(false)
            .redirectedPredicator(comboProvider
                    .addDefaultConditional(new ProviderConditional(ProviderConditionalType.DEFAULT, null, false))
                    .exportCombination()
            );

    public static CapabilityItem.Builder getBuilder()
    {
        return greatswordBuilder;
    }
    public static EFBSWeaponCapability.Builder modifyBuilder()
    {
        return greatswordBuilder;
    }

    public static void RegisterMods()
    {
        for (ProviderConditional pr : styleProviderRegistry)
        {
            getStyleProvider().addConditional(pr);
        }
        for (ProviderConditional pr : comboProviderRegistry)
        {
            getStyleProvider().addConditional(pr);
        }
    }

    public static StyleComboProvider getStyleProvider()
    {
        return STYLE_COMBO_PROVIDER;
    }
    public static ComboProvider getComboProvider()
    {
        return comboProvider;
    }
}
