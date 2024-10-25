package net.forixaim.battle_arts.core_assets.skills.active.ultimate_arts;

import com.google.common.collect.Lists;
import net.forixaim.battle_arts.core_assets.skills.active.burst_arts.SimpleBurstArt;
import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillCategories;
import net.forixaim.bs_api.battle_arts_skills.active.ActiveSkill;
import net.forixaim.bs_api.battle_arts_skills.active.ultimate_arts.UltimateArt;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.List;

public class SimpleUltimateArt extends UltimateArt
{
    public static class Builder extends Skill.Builder<SimpleUltimateArt> {
        protected AttackAnimationProvider attackAnimation;
        protected List<WeaponCategory> allowedWeapons = Lists.newArrayList();

        public SimpleUltimateArt.Builder addWeaponCategory(WeaponCategory category)
        {
            allowedWeapons.add(category);
            return this;
        }

        public SimpleUltimateArt.Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public SimpleUltimateArt.Builder setActivateType(Skill.ActivateType activateType) {
            this.activateType = activateType;
            return this;
        }

        public SimpleUltimateArt.Builder setResource(Skill.Resource resource) {
            this.resource = resource;
            return this;
        }

        public SimpleUltimateArt.Builder setAnimations(AttackAnimationProvider provider) {
            this.attackAnimation = provider;
            return this;
        }
    }

    public static SimpleBurstArt.Builder createSimpleCombatArt() {
        return (new SimpleBurstArt.Builder()).setCategory(BattleArtsSkillCategories.ULTIMATE_ART).setResource(Skill.Resource.COOLDOWN);
    }

    protected AttackAnimationProvider attackAnimation;

    public SimpleUltimateArt(SimpleUltimateArt.Builder builder) {
        super(builder);
        this.allowedWeapons = builder.allowedWeapons;
        this.attackAnimation = builder.attackAnimation;
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args)
    {
        executer.playAnimationSynchronized(this.attackAnimation.get(), 0);
        super.executeOnServer(executer, args);
    }

    @Override
    public UltimateArt registerPropertiesToAnimation() {
        AttackAnimation anim = this.attackAnimation.get();

        for (AttackAnimation.Phase phase : anim.phases) {
            phase.addProperties(this.properties.get(0).entrySet());
        }

        return this;
    }
}
