package net.forixaim.battle_arts.core_assets.skills.active.burst_arts;

import com.google.common.collect.Lists;
import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillCategories;
import net.forixaim.bs_api.battle_arts_skills.active.burst_arts.BurstArt;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.List;

public class SimpleBurstArt extends BurstArt
{
    public static class Builder extends Skill.Builder<SimpleBurstArt> {
        protected AttackAnimationProvider attackAnimation;
        protected List<WeaponCategory> allowedWeapons = Lists.newArrayList();

        public SimpleBurstArt.Builder addWeaponCategory(WeaponCategory category)
        {
            allowedWeapons.add(category);
            return this;
        }

        public SimpleBurstArt.Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public SimpleBurstArt.Builder setActivateType(Skill.ActivateType activateType) {
            this.activateType = activateType;
            return this;
        }

        public SimpleBurstArt.Builder setResource(Skill.Resource resource) {
            this.resource = resource;
            return this;
        }

        public SimpleBurstArt.Builder setAnimations(AttackAnimationProvider provider) {
            this.attackAnimation = provider;
            return this;
        }
    }

    public static SimpleBurstArt.Builder createSimpleCombatArt() {
        return (new SimpleBurstArt.Builder()).setCategory(BattleArtsSkillCategories.BURST_ART).setResource(Skill.Resource.COOLDOWN);
    }

    protected AttackAnimationProvider attackAnimation;

    public SimpleBurstArt(SimpleBurstArt.Builder builder) {
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
    public BurstArt registerPropertiesToAnimation() {
        AttackAnimation anim = this.attackAnimation.get();

        for (AttackAnimation.Phase phase : anim.phases) {
            phase.addProperties(this.properties.get(0).entrySet());
        }

        return this;
    }
}
