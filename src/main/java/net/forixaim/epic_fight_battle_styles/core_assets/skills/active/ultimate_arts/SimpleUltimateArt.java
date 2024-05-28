package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ultimate_arts;

import com.google.common.collect.Lists;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts.SimpleBurstArt;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.List;

public class SimpleUltimateArt extends ActiveSkill
{
    public static class Builder extends Skill.Builder<SimpleUltimateArt> {
        protected ResourceLocation attackAnimation;
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

        public SimpleUltimateArt.Builder setAnimations(ResourceLocation attackAnimation) {
            this.attackAnimation = attackAnimation;
            return this;
        }
    }

    public static SimpleBurstArt.Builder createSimpleCombatArt() {
        return (new SimpleBurstArt.Builder()).setCategory(EpicFightBattleStyleSkillCategories.ULTIMATE_ART).setResource(Skill.Resource.COOLDOWN);
    }

    protected AnimationProvider.AttackAnimationProvider attackAnimation;

    public SimpleUltimateArt(SimpleUltimateArt.Builder builder) {
        super(builder);
        this.allowedWeapons = builder.allowedWeapons;
        this.attackAnimation = () -> (AttackAnimation) EpicFightMod.getInstance().animationManager.findAnimationByPath(builder.attackAnimation.toString());
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args)
    {
        executer.playAnimationSynchronized(this.attackAnimation.get(), 0);
        super.executeOnServer(executer, args);
    }

    @Override
    public ActiveSkill registerPropertiesToAnimation() {
        AttackAnimation anim = this.attackAnimation.get();

        for (AttackAnimation.Phase phase : anim.phases) {
            phase.addProperties(this.properties.get(0).entrySet());
        }

        return this;
    }
}
