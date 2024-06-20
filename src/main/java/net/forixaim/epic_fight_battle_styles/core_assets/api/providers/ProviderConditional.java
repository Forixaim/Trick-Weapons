package net.forixaim.epic_fight_battle_styles.core_assets.api.providers;

import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.HelperFunctions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import javax.annotation.Nullable;

public class ProviderConditional
{
    private final ProviderConditionalType type;
    private final Style style;
    private final Boolean combination;
    private final Skill skill;
    private final WeaponCategory category;
    private final Item weapon;
    private final ProviderConditional[] providerConditionals;
    private final SkillSlot slot;
    private final SkillDataKey<Boolean> key;

    public ProviderConditional(ProviderConditionalType type, @Nullable Style style, @Nullable Skill skill, @Nullable WeaponCategory category, @Nullable Item item, @Nullable SkillSlot slot, @Nullable SkillDataKey<Boolean> key, @Nullable Boolean combo, ProviderConditional... conditionals)
    {
        this.type = type;
        this.style = style;
        this.skill = skill;
        this.category = category;
        this.weapon = item;
        this.slot = slot;
        this.key = key;
        this.combination = combo;
        this.providerConditionals = conditionals;

    }

    public ProviderConditional(ProviderConditionalType type, Style style, @Nullable Skill skill, @Nullable WeaponCategory category, @Nullable Item item, @Nullable SkillSlot slot, @Nullable SkillDataKey<Boolean> key)
    {
        this.type = type;
        this.style = style;
        this.skill = skill;
        this.category = category;
        this.weapon = item;
        this.slot = slot;
        this.key = key;
        this.combination = null;
        this.providerConditionals = null;
    }

    /**
     * @param type MUST be DEFAULT
     * @param style a style of your choosing.
     */
    public ProviderConditional(ProviderConditionalType type, Style style)
    {
        this.type = type;
        this.style = style;
        this.skill = null;
        this.category = null;
        this.weapon = null;
        this.slot = null;
        this.key = null;
        this.combination = null;
        this.providerConditionals = null;
    }

    /**
     * @param type MUST be COMPOSITE
     * @param style a style of your choosing.
     */
    public ProviderConditional(ProviderConditionalType type, Style style, ProviderConditional... conditionals)
    {
        this.type = type;
        this.style = style;
        this.providerConditionals = conditionals;
        this.skill = null;
        this.category = null;
        this.weapon = null;
        this.slot = null;
        this.combination = null;
        this.key = null;
    }

    /**
     *
     * @param entityPatch the patch used to return whatever it is.
     * @return if the conditionals ever evaluate to true.
     */
    public Boolean testConditionalBoolean(LivingEntityPatch<?> entityPatch) {
        if (type.equals(ProviderConditionalType.SKILL)) {
            if (HelperFunctions.skillCheck(entityPatch, skill)) {
                return true;
            }
        }
        if (type.equals(ProviderConditionalType.WEAPON_CATEGORY)) {
            if (HelperFunctions.offHandItem(entityPatch, category)) {
                return true;
            }
        }
        if (type.equals(ProviderConditionalType.DATA_KEY)) {
            if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                return playerPatch.getSkill(slot).getDataManager().hasData(key) && playerPatch.getSkill(slot).getDataManager().getDataValue(key);
            }
        }
        if (type.equals(ProviderConditionalType.SPECIFIC_WEAPON))
        {
            return entityPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(WOMItems.GESETZ.get());
        }
        if (type.equals(ProviderConditionalType.COMPOSITE))
        {
            assert providerConditionals != null;
            for (ProviderConditional conditional : providerConditionals)
            {
                if (!conditional.testConditionalBoolean(entityPatch))
                {
                    return false;
                }
            }
            return true;
        }
        return type.equals(ProviderConditionalType.DEFAULT);
    }

    public Style testConditionalStyle(LivingEntityPatch<?> entityPatch) {
        if (type.equals(ProviderConditionalType.SKILL)) {
            if (HelperFunctions.skillCheck(entityPatch, skill)) {
                return style;
            }
        }
        if (type.equals(ProviderConditionalType.WEAPON_CATEGORY)) {
            if (HelperFunctions.offHandItem(entityPatch, category)) {
                return style;
            }
        }
        if (type.equals(ProviderConditionalType.DATA_KEY)) {
            if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                if (playerPatch.getSkill(slot).getDataManager().hasData(key) && playerPatch.getSkill(slot).getDataManager().getDataValue(key))
                {
                    return style;
                }
            }
        }
        if (type.equals(ProviderConditionalType.SPECIFIC_WEAPON))
        {
            if (entityPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(this.weapon))
            {
                return style;
            }
        }
        if (type.equals(ProviderConditionalType.COMPOSITE))
        {
            assert providerConditionals != null;
            for (ProviderConditional conditional : providerConditionals)
            {
                if (!conditional.testConditionalBoolean(entityPatch))
                {
                    return null;
                }
            }
            return style;
        }
        if (type.equals(ProviderConditionalType.DEFAULT))
        {
            return style;
        }
        return null;
    }

    public Boolean testConditionalCombo(LivingEntityPatch<?> entityPatch) {
        if (type.equals(ProviderConditionalType.SKILL)) {
            if (HelperFunctions.skillCheck(entityPatch, skill)) {
                return combination;
            }
        }
        if (type.equals(ProviderConditionalType.WEAPON_CATEGORY)) {
            if (HelperFunctions.offHandItem(entityPatch, category)) {
                return combination;
            }
        }
        if (type.equals(ProviderConditionalType.DATA_KEY)) {
            if (entityPatch instanceof PlayerPatch<?> playerPatch) {
                if (playerPatch.getSkill(slot).getDataManager().hasData(key) && playerPatch.getSkill(slot).getDataManager().getDataValue(key))
                {
                    return combination;
                }
            }
        }
        if (type.equals(ProviderConditionalType.SPECIFIC_WEAPON))
        {
            if (entityPatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(this.weapon))
            {
                return combination;
            }
        }
        if (type.equals(ProviderConditionalType.COMPOSITE))
        {
            assert providerConditionals != null;
            for (ProviderConditional conditional : providerConditionals)
            {
                if (!conditional.testConditionalBoolean(entityPatch))
                {
                    return null;
                }
            }
            return combination;
        }
        if (type.equals(ProviderConditionalType.DEFAULT))
        {
            return combination;
        }
        return null;
    }
}
