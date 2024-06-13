package net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts;

import com.google.common.collect.Lists;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.ActiveSkill;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.combat_arts.SimpleCombatArt;
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

public class SimpleBurstArt extends BurstArt
{
    public static class Builder extends Skill.Builder<SimpleBurstArt> {
        protected ResourceLocation attackAnimation;
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

        public SimpleBurstArt.Builder setAnimations(ResourceLocation attackAnimation) {
            this.attackAnimation = attackAnimation;
            return this;
        }
    }

    public static SimpleBurstArt.Builder createSimpleCombatArt() {
        return (new SimpleBurstArt.Builder()).setCategory(EpicFightBattleStyleSkillCategories.BURST_ART).setResource(Skill.Resource.COOLDOWN);
    }

    protected AnimationProvider.AttackAnimationProvider attackAnimation;

    public SimpleBurstArt(SimpleBurstArt.Builder builder) {
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
    public BurstArt registerPropertiesToAnimation() {
        AttackAnimation anim = this.attackAnimation.get();

        for (AttackAnimation.Phase phase : anim.phases) {
            phase.addProperties(this.properties.get(0).entrySet());
        }

        return this;
    }
}
