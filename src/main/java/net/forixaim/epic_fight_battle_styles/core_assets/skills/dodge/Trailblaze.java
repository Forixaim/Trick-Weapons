package net.forixaim.epic_fight_battle_styles.core_assets.skills.dodge;

import io.netty.buffer.Unpooled;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.active.burst_arts.FlareBurst;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.legendary.ImperatriceLumiere;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class Trailblaze extends DodgeSkill
{
    private static final UUID EVENT_UUID = UUID.fromString("c1b3d7b3-f934-48b5-a03e-9a94ba1962a6");

    public Trailblaze(DodgeSkill.Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.COMBO_COUNTER_HANDLE_EVENT, EVENT_UUID, (event) -> {
            if (event.getCausal() == ComboCounterHandleEvent.Causal.ACTION_ANIMATION_RESET && event.getAnimation().in(this.animations)) {
                event.setNextValue(event.getPrevValue());
            }
        });
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, event ->
        {
            if (container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) && container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.FLARE_BURST.get()) && container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.FLARE_BURST.get()))
            {
                if (container.getExecuter().getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getSkill() instanceof ImperatriceLumiere imperatriceLumiere)
                {
                    imperatriceLumiere.triggerIgnitionRiposte(container.getExecuter(), container.getSlot(), event.getDamageSource());
                }
            }
        });
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executor) {
        return executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).hasSkill(SkillRegistry.IMPERATRICE_LUMIERE) &&
                !executor.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.ULTIMATE_ART_ACTIVE.get());
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
}
