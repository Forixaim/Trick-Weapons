package net.forixaim.epic_fight_battle_styles.core_assets.api.providers;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This class is meant to be as an extendbale
 */
public class StyleProvider
{
    private final List<ProviderConditional> conditionals;

    public StyleProvider()
    {
        conditionals = new ArrayList<ProviderConditional>();
    }

    public StyleProvider addConditional(ProviderConditional conditional)
    {
        this.conditionals.add(conditional);
        return this;
    }

    /**
     * @throws NullPointerException if none of the provided Conditionals return a Style;
     * @return The Function that is used for the StyleProvider
     */
    public Function<LivingEntityPatch<?>, Style> export()
    {
        Function<LivingEntityPatch<?>, Style> providerFunction = (entityPatch) ->
        {
            for (ProviderConditional conditional : conditionals)
            {
                if (conditional.testConditionalStyle(entityPatch) != null)
                {
                    return conditional.testConditionalStyle(entityPatch);
                }
            }
            return null;
        };
        return providerFunction;
    }
}
