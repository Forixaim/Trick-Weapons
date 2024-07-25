package net.forixaim.epic_fight_battle_styles.mixins.client_engine_extenders;

import net.forixaim.epic_fight_battle_styles.core_assets.client.KeyBinds;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;

import java.util.Map;
import java.util.function.BiFunction;

@Mixin(value = ControllEngine.class, remap = false)
public abstract class MixinControlEngine
{
	@Unique
	ControllEngine epic_fight_battle_styles$controlEngine = null;

	@Shadow(remap = false) private LocalPlayerPatch playerpatch;

	@Shadow(remap = false) private KeyMapping currentChargingKey;

	@Shadow(remap = false) public Options options;

	@Shadow(remap = false) private LocalPlayer player;

	@Shadow(remap = false) private boolean sneakPressToggle;

	@Shadow(remap = false) protected abstract void reserveKey(SkillSlot slot, KeyMapping keyMapping);

	@Shadow(remap = false) public abstract void lockHotkeys();

	@Inject(method = "<init>", at = @At("RETURN"))
	public void ConHead(CallbackInfo ci)
	{
		epic_fight_battle_styles$controlEngine = (ControllEngine) (Object)this;
	}

	@Inject(method = "handleEpicFightKeyMappings", at = @At("HEAD"), remap = false)
	public void epic_fight_battle_styles$handleMappings(CallbackInfo ci)
	{
		while (KeyBinds.USE_ART_1.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != KeyBinds.USE_ART_1)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.USE_ART_1.getKey()))
				{
					SkillContainer skill = this.playerpatch.getSkill(EpicFightBattleStyleSkillSlots.COMBAT_ART_1);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(EpicFightBattleStyleSkillSlots.COMBAT_ART_1, KeyBinds.USE_ART_1);
					}
				}
			}
		}
		while (KeyBinds.USE_ART_2.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != KeyBinds.USE_ART_2)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.USE_ART_2.getKey()))
				{
					SkillContainer skill = this.playerpatch.getSkill(EpicFightBattleStyleSkillSlots.COMBAT_ART_2);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(EpicFightBattleStyleSkillSlots.COMBAT_ART_2, KeyBinds.USE_ART_2);
					}
				}
			}
		}
		while (KeyBinds.USE_ULTIMATE_ART.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != KeyBinds.USE_ULTIMATE_ART)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.USE_ULTIMATE_ART.getKey()))
				{
					SkillContainer skill = this.playerpatch.getSkill(EpicFightBattleStyleSkillSlots.ULTIMATE_ART);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(EpicFightBattleStyleSkillSlots.ULTIMATE_ART, KeyBinds.USE_ULTIMATE_ART);
					}
				}
			}
		}
		while (KeyBinds.USE_BURST_ART.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != KeyBinds.USE_BURST_ART)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.USE_BURST_ART.getKey()))
				{
					SkillContainer skill = this.playerpatch.getSkill(EpicFightBattleStyleSkillSlots.BURST_ART);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(EpicFightBattleStyleSkillSlots.BURST_ART, KeyBinds.USE_BURST_ART);
					}
				}
			}
		}
	}
	@Unique
	private boolean epic_fight_battle_styles$executeRequest(SkillContainer skill) {
		return skill.sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).shouldReserverKey();
	}
}
