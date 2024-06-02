package net.forixaim.epic_fight_battle_styles.mixins.patches;


import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPChangeLivingMotion;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mixin(value = ServerPlayerPatch.class, remap = false)
public abstract class MixinServerPlayerPatch
{
    @Unique
    ServerPlayerPatch epic_fight_battle_styles$animatorGetter = (ServerPlayerPatch)(Object)this;
    @Shadow private boolean updatedMotionCurrentTick;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void ConstructorHead(CallbackInfo ci)
    {

    }

    @Inject(method = "updateHeldItem", at = @At("TAIL"))
    public void epic_fight_battle_styles$updateBattleStyle(CapabilityItem fromCap, CapabilityItem toCap, ItemStack from, ItemStack _to, InteractionHand hand, CallbackInfo ci)
    {
        this.epic_fight_battle_styles$modifyLivingMotionByBattleStyle();
    }

    @Unique
    public void epic_fight_battle_styles$modifyLivingMotionByBattleStyle()
    {
        if (this.updatedMotionCurrentTick)
        {
            return;
        }
        epic_fight_battle_styles$animatorGetter.getAnimator().resetLivingAnimations();
        Map<LivingMotion, StaticAnimation> motionModifier;
        Skill style = epic_fight_battle_styles$animatorGetter.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getSkill();
        if (style instanceof BattleStyle battleStyle)
        {
            motionModifier = battleStyle.getLivingMotionModifiers(epic_fight_battle_styles$animatorGetter);
            for (Map.Entry<LivingMotion, StaticAnimation> entry : motionModifier.entrySet()) {
                epic_fight_battle_styles$animatorGetter.getAnimator().addLivingAnimation(entry.getKey(), entry.getValue());
            }
            SPChangeLivingMotion msg = new SPChangeLivingMotion(epic_fight_battle_styles$animatorGetter.getOriginal().getId());
            msg.putEntries(epic_fight_battle_styles$animatorGetter.getAnimator().getLivingAnimationEntrySet());
            EpicFightNetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, epic_fight_battle_styles$animatorGetter.getOriginal());
            this.updatedMotionCurrentTick = true;
        }
    }
}
