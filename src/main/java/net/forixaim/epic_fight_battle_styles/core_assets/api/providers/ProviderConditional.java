package net.forixaim.epic_fight_battle_styles.core_assets.api.providers;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import javax.annotation.Nullable;
import java.util.function.Function;

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
	private final InteractionHand hand;
	private final Function<LivingEntityPatch<?>, Boolean> customFunction;

	/**
	 *
	 * @param type Must be SKILL_EXISTENCE, SKILL_ACTIVATION, or DATA_KEY
	 * @param style the style of your choosing
	 * @param skill The Skill in specific.
	 * @param slot Skill Slot corresponding to the skill.
	 * @param key Data keys associated with the Skill
	 * @param combo weapon predicate combo.
	 */
	public ProviderConditional(@NotNull ProviderConditionalType type, @Nullable Style style, @Nullable Skill skill, @Nullable SkillSlot slot, @Nullable SkillDataKey<Boolean> key, @Nullable Boolean combo)
	{
		this.type = type;
		this.style = style;
		this.skill = skill;
		this.category = null;
		this.weapon = null;
		this.slot = slot;
		this.key = key;
		this.combination = combo;
		this.providerConditionals = null;
		this.hand = null;
		this.customFunction = null;
	}

	/**
	 *
	 * @param type must be WEAPON_CATEGORY or SPECIFIC_WEAPON
	 * @param style the Style of your choosing
	 * @param hand the InteractionHand of the item
	 * @param category The weapon category
	 * @param item Any Specific Item
	 * @param combo The combo predicate logic whenever this is the true or false for dual wielding.
	 */
	public ProviderConditional(@NotNull ProviderConditionalType type, Style style, @NotNull InteractionHand hand, @Nullable WeaponCategory category, @Nullable Item item, Boolean combo)
	{
		this.type = type;
		this.style = style;
		this.skill = null;
		this.category = category;
		this.weapon = item;
		this.slot = null;
		this.key = null;
		this.combination = combo;
		this.providerConditionals = null;
		this.hand = hand;
		this.customFunction = null;
	}

	/**
	 * @param type  MUST be DEFAULT
	 * @param style a style of your choosing.
	 * @param combination The combo predicate logic whenever this is the true or false for dual wielding.
	 */
	public ProviderConditional(@NotNull ProviderConditionalType type, Style style, Boolean combination)
	{
		this.type = type;
		this.style = style;
		this.skill = null;
		this.category = null;
		this.weapon = null;
		this.slot = null;
		this.key = null;
		this.combination = combination;
		this.providerConditionals = null;
		this.hand = null;
		this.customFunction = null;
	}

	/**
	 * @param type  MUST be COMPOSITE
	 * @param style a style of your choosing.
	 * @param combination The combo predicate logic whenever this is the true or false for dual wielding.
	 */
	public ProviderConditional(@NotNull ProviderConditionalType type, Style style, Boolean combination, ProviderConditional... conditionals)
	{
		this.type = type;
		this.style = style;
		this.providerConditionals = conditionals;
		this.skill = null;
		this.category = null;
		this.weapon = null;
		this.slot = null;
		this.combination = combination;
		this.key = null;
		this.hand = null;
		this.customFunction = null;
	}

	/**
	 * @param type Must be CUSTOM
	 * @param style the Style of the end result
	 * @param combination The combo predicate logic whenever this is the true or false for dual wielding.
	 * @param booleanFunction a custom function to evaluate if true.
	 */
	public ProviderConditional(@NotNull ProviderConditionalType type, Style style, Boolean combination, Function<LivingEntityPatch<?>, Boolean> booleanFunction)
	{
		this.type = type;
		this.style = style;
		this.skill = null;
		this.category = null;
		this.hand = null;
		this.providerConditionals = null;
		this.key = null;
		this.combination = combination;
		this.slot = null;
		this.weapon = null;
		this.customFunction = booleanFunction;
	}

	/**
	 * @param entityPatch the patch used to return whatever it is.
	 * @return if the conditionals ever evaluate to true.
	 */
	public Boolean testConditionalBoolean(LivingEntityPatch<?> entityPatch)
	{
		if (type.equals(ProviderConditionalType.SKILL_ACTIVATION))
		{
			if (entityPatch instanceof PlayerPatch<?> pPatch)
			{
				if (pPatch.getSkill(slot).isActivated())
				{
					return true;
				}
			}
		}
		if (type.equals(ProviderConditionalType.SKILL_EXISTENCE))
		{
			if (HelperFunctions.skillCheck(entityPatch, skill, slot))
			{
				return true;
			}
		}
		if (type.equals(ProviderConditionalType.WEAPON_CATEGORY))
		{
			if (HelperFunctions.itemCheck(entityPatch, category, hand))
			{
				return true;
			}
		}
		if (type.equals(ProviderConditionalType.DATA_KEY))
		{
			if (entityPatch instanceof PlayerPatch<?> playerPatch)
			{
				return playerPatch.getSkill(slot).getDataManager().hasData(key) && playerPatch.getSkill(slot).getDataManager().getDataValue(key);
			}
		}
		if (type.equals(ProviderConditionalType.SPECIFIC_WEAPON))
		{
			assert hand != null;
			return entityPatch.getOriginal().getItemInHand(hand).is(weapon);
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
		if (type.equals(ProviderConditionalType.CUSTOM))
		{
			assert this.customFunction != null;
			if (this.customFunction.apply(entityPatch))
			{
				return true;
			}
		}
		return type.equals(ProviderConditionalType.DEFAULT);
	}

	public Style testConditionalStyle(LivingEntityPatch<?> entityPatch)
	{
		if (type.equals(ProviderConditionalType.SKILL_ACTIVATION))
		{
			if (entityPatch instanceof PlayerPatch<?> pPatch)
			{
				if (pPatch.getSkill(slot).isActivated())
				{
					return style;
				}
			}
		}
		if (type.equals(ProviderConditionalType.SKILL_EXISTENCE))
		{
			if (HelperFunctions.skillCheck(entityPatch, skill, slot))
				return style;
		}
		if (type.equals(ProviderConditionalType.WEAPON_CATEGORY))
		{
			if (HelperFunctions.itemCheck(entityPatch, category, hand))
				return style;
		}
		if (type.equals(ProviderConditionalType.DATA_KEY))
		{
			if (entityPatch instanceof PlayerPatch<?> playerPatch)
			{
				if (playerPatch.getSkill(slot).getDataManager().hasData(key) && playerPatch.getSkill(slot).getDataManager().getDataValue(key))
					return style;
			}
		}
		if (type.equals(ProviderConditionalType.SPECIFIC_WEAPON))
		{
			assert hand != null;
			if (entityPatch.getOriginal().getItemInHand(hand).is(this.weapon))
				return style;
		}
		if (type.equals(ProviderConditionalType.COMPOSITE))
		{
			assert providerConditionals != null;
			for (ProviderConditional conditional : providerConditionals)
			{
				if (!conditional.testConditionalBoolean(entityPatch))
					return null;
			}
			return style;
		}
		if (type.equals(ProviderConditionalType.CUSTOM))
		{
			assert this.customFunction != null;
			if (this.customFunction.apply(entityPatch))
			{
				return style;
			}
		}
		if (type.equals(ProviderConditionalType.DEFAULT))
			return style;
		return null;
	}

	public Boolean testConditionalCombo(LivingEntityPatch<?> entityPatch)
	{
		if (type.equals(ProviderConditionalType.SKILL_ACTIVATION))
		{
			if (entityPatch instanceof PlayerPatch<?> pPatch)
			{
				if (pPatch.getSkill(slot).isActivated())
				{
					return combination;
				}
			}
		}
		if (type.equals(ProviderConditionalType.SKILL_EXISTENCE))
		{
			if (HelperFunctions.skillCheck(entityPatch, skill, slot))
				return combination;
		}
		if (type.equals(ProviderConditionalType.WEAPON_CATEGORY))
		{
			if (HelperFunctions.itemCheck(entityPatch, category, hand))
				return combination;
		}
		if (type.equals(ProviderConditionalType.DATA_KEY))
		{
			if (entityPatch instanceof PlayerPatch<?> playerPatch)
			{
				if (playerPatch.getSkill(slot).getDataManager().hasData(key) && playerPatch.getSkill(slot).getDataManager().getDataValue(key))
					return combination;
			}
		}
		if (type.equals(ProviderConditionalType.SPECIFIC_WEAPON))
		{
			assert hand != null;
			if (entityPatch.getOriginal().getItemInHand(hand).is(this.weapon))
				return combination;
		}
		if (type.equals(ProviderConditionalType.COMPOSITE))
		{
			assert providerConditionals != null;
			for (ProviderConditional conditional : providerConditionals)
			{
				if (!conditional.testConditionalBoolean(entityPatch))
					return null;
			}
			return combination;
		}
		if (type.equals(ProviderConditionalType.CUSTOM))
		{
			assert this.customFunction != null;
			if (this.customFunction.apply(entityPatch))
			{
				return combination;
			}
		}
		if (type.equals(ProviderConditionalType.DEFAULT))
			return combination;
		return null;
	}
}
