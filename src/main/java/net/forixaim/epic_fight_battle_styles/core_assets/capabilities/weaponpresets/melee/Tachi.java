package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditional;
import net.forixaim.epic_fight_battle_styles.core_assets.api.providers.StyleComboProvider;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.EFBSWeaponCapability;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.function.Function;

import static net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType.DEFAULT;
import static net.forixaim.epic_fight_battle_styles.core_assets.api.providers.ProviderConditionalType.SKILL_EXISTENCE;

public class Tachi
{

    public static StyleComboProvider styleComboProvider = new StyleComboProvider()
            .addConditional(new ProviderConditional(SKILL_EXISTENCE, ImperatriceLumiereStyles.IMPERATRICE_SWORD, SkillRegistry.IMPERATRICE_LUMIERE, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, null));

    public static ComboProvider comboProvider = new ComboProvider()
            .addConditional(new ProviderConditional(SKILL_EXISTENCE, null, SkillRegistry.IMPERATRICE_LUMIERE, EpicFightBattleStyleSkillSlots.BATTLE_STYLE, null, false));


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

    private static final EFBSWeaponCapability.Builder tachiBuilder = EFBSWeaponCapability.builder()
            .redirectedCategory(CapabilityItem.WeaponCategories.TACHI)
			.redirectedCollider(ColliderPreset.TACHI)
			.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
            .redirectedSwingSound(EpicFightSounds.WHOOSH.get())
            .redirectedProvider(Tachi.styleComboProvider.addDefaultConditional(
                    new ProviderConditional(DEFAULT, CapabilityItem.Styles.TWO_HAND, null)
            ).exportStyle())
			.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Tachi.defaultTachiAttack)
			.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
            .createStyleCategory(CapabilityItem.Styles.MOUNT, Sword.mountedAttack)
            .offHandUse(false);

    public static CapabilityItem.Builder getBuilder()
    {
        return tachiBuilder;
    }

    public static EFBSWeaponCapability.Builder modifyBuilder()
    {
        return tachiBuilder;
    }
    public static StyleComboProvider getStyleProvider()
    {
        return styleComboProvider;
    }
    public static ComboProvider getComboProvider()
    {
        return comboProvider;
    }
}
