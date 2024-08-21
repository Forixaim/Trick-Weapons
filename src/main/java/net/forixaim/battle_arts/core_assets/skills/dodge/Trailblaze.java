package net.forixaim.battle_arts.core_assets.skills.dodge;

import net.forixaim.battle_arts.Config;
import net.forixaim.battle_arts.core_assets.animations.BattleAnimations;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillSlots;
import net.forixaim.battle_arts.initialization.registry.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class Trailblaze extends DodgeSkill
{
    private static final UUID EVENT_UUID = UUID.fromString("c1b3d7b3-f934-48b5-a03e-9a94ba1962a6");

    private static final AnimationProvider<DodgeAnimation> SPOT_DODGE = () -> (DodgeAnimation) BattleAnimations.IMPERATRICE_TRAILBLAZE_SPOT;
    private static final AnimationProvider<AttackAnimation> SPOT_DODGE_RIPOSTE = () -> (AttackAnimation) BattleAnimations.IMPERATRICE_SWORD_JAB3;

    public Trailblaze(DodgeSkill.Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.COMBO_COUNTER_HANDLE_EVENT, EVENT_UUID, (event) -> {
            if (event.getCausal() == ComboCounterHandleEvent.Causal.ANOTHER_ACTION_ANIMATION && event.getAnimation().in(this.animations)) {
                event.setNextValue(event.getPrevValue());
            }
        });
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, event ->
        {
            if ((container.getExecuter().getSkill(BattleArtsSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) && container.getExecuter().getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().hasData(BattleArtsDataKeys.FLARE_BURST.get()) && container.getExecuter().getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(BattleArtsDataKeys.FLARE_BURST.get())) || Config.unconditionalRiposte)
            {
                if (event.getPlayerPatch().getOriginal().isInvisible())
                {
                    event.getPlayerPatch().getOriginal().setInvisible(false);
                }
                if (container.getExecuter().getSkill(this).getDataManager().getDataValue(BattleArtsDataKeys.SPOT_DODGE.get()))
                {
                    if (event.getDamageSource().getEntity() instanceof LivingEntity attacker)
                    {
                        if (EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class) != null)
                        {
                            event.getPlayerPatch().playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 0, 0);
                            EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class).applyStun(StunType.HOLD, 1);
                            event.getPlayerPatch().playAnimationSynchronized(SPOT_DODGE_RIPOSTE.get(), 0.2f);
                        }
                    }

                }
            }
        });
    }



    @Override
    public boolean canExecute(PlayerPatch<?> executor) {
        return executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) &&
                !executor.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(BattleArtsDataKeys.ULTIMATE_ART_ACTIVE.get()) &&
                executor.getSkill(SkillSlots.WEAPON_INNATE).getDataManager().hasData(BattleArtsDataKeys.CHARGE_EXECUTING.get()) && !executor.getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(BattleArtsDataKeys.CHARGE_EXECUTING.get());
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.COMBO_COUNTER_HANDLE_EVENT, EVENT_UUID);
    }

    @OnlyIn(Dist.CLIENT)
    public Object getExecutionPacket(LocalPlayerPatch executer, FriendlyByteBuf args) {
        Input input = ((LocalPlayer)executer.getOriginal()).input;
        float pulse = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus((LivingEntity)executer.getOriginal()), 0.0F, 1.0F);
        input.tick(false, pulse);
        int forward = input.up ? 1 : 0;
        int backward = input.down ? -1 : 0;
        int left = input.left ? 1 : 0;
        int right = input.right ? -1 : 0;
        int vertic = forward + backward;
        int horizon = left + right;
        float yRot = Minecraft.getInstance().gameRenderer.getMainCamera().getYRot();
        float degree = (float)(-(horizon * (1 - Math.abs(vertic)) + 45 * vertic * horizon)) + yRot;
        int animation;
        if (vertic == 0) {
            if (horizon == 0) {
                animation = 0;
            } else {
                animation = horizon >= 0 ? 2 : 3;
            }
        } else {
            animation = vertic >= 0 ? 0 : 1;
        }

        CPExecuteSkill packet = new CPExecuteSkill(executer.getSkill(this).getSlotId());
        packet.getBuffer().writeInt(animation);
        packet.getBuffer().writeFloat(degree);
        return packet;
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args)
    {
        if (executer.getOriginal().isShiftKeyDown())
        {
            executer.getSkill(this).getDataManager().setDataSync(BattleArtsDataKeys.SPOT_DODGE.get(), true, executer.getOriginal());
            executer.playAnimationSynchronized(SPOT_DODGE.get(), 0);
        }
        else
        {
            executer.getSkill(this).getDataManager().setDataSync(BattleArtsDataKeys.SPOT_DODGE.get(), false, executer.getOriginal());
            super.executeOnServer(executer, args);
        }
    }
}
