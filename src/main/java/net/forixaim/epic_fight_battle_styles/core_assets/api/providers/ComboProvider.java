package net.forixaim.epic_fight_battle_styles.core_assets.api.providers;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ComboProvider
{
    private final List<ProviderConditional> conditionals;

    public ComboProvider()
    {
        conditionals = new ArrayList<ProviderConditional>();
    }

    public ComboProvider addConditional(ProviderConditional conditional)
    {
        this.conditionals.add(conditional);
        return this;
    }

    /**
     * @throws NullPointerException if none of the provided Conditionals return a Style;
     * @return The Function that is used for the StyleProvider
     */
    public Function<LivingEntityPatch<?>, Boolean> export()
    {
        Function<LivingEntityPatch<?>, Boolean> providerFunction = (entityPatch) ->
        {
            for (ProviderConditional conditional : conditionals)
            {
                if (conditional.testConditionalCombo(entityPatch) != null)
                {
                    return conditional.testConditionalCombo(entityPatch);
                }
            }
            return null;
        };
        return providerFunction;
    }
}
