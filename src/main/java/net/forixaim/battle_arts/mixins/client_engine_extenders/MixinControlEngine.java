package net.forixaim.battle_arts.mixins.client_engine_extenders;

import net.forixaim.battle_arts.core_assets.animations.AnimationHelpers;
import net.forixaim.battle_arts.core_assets.client.KeyBinds;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsSkillSlots;
import net.forixaim.battle_arts.core_assets.skills.aerials.MidAirAttack;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.gui.screen.SkillEditScreen;
import yesman.epicfight.client.gui.screen.config.IngameConfigurationScreen;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.entity.eventlistener.SkillExecuteEvent;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

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

	@Shadow private boolean attackLightPressToggle;

	@Shadow protected abstract void releaseAllServedKeys();

	@Shadow private boolean weaponInnatePressToggle;

	@Shadow private int weaponInnatePressCounter;


	@Shadow private boolean moverPressToggle;

	@Shadow @Final private Minecraft minecraft;


	@Shadow private int sneakPressCounter;


	@Shadow private int lastHotbarLockedTime;

	@Shadow private boolean hotbarLocked;

	@Shadow public abstract void unlockHotkeys();

	@Shadow private SkillSlot reservedOrChargingSkillSlot;

	@Shadow private boolean chargeKeyUnpressed;

	@Shadow private KeyMapping reservedKey;

	@Shadow private int reserveCounter;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void ConHead(CallbackInfo ci)
	{
		epic_fight_battle_styles$controlEngine = (ControllEngine) (Object)this;
	}

	/**
	 * @author Forixaim
	 * @reason To make aerials more strict.
	 */
	@Overwrite(remap = false)
	public void handleEpicFightKeyMappings()
	{
		if (EpicFightKeyMappings.SKILL_EDIT.consumeClick())
		{
			if (this.playerpatch.getSkillCapability() != null)
			{
				Minecraft.getInstance().setScreen(new SkillEditScreen(this.player, this.playerpatch.getSkillCapability()));
			}
		}

		if (EpicFightKeyMappings.CONFIG.consumeClick())
		{
			Minecraft.getInstance().setScreen(new IngameConfigurationScreen(this.minecraft, null));
		}

		while (KeyBinds.USE_ART_1.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != KeyBinds.USE_ART_1)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.USE_ART_1.getKey()))
				{
					SkillContainer skill = this.playerpatch.getSkill(BattleArtsSkillSlots.COMBAT_ART);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(BattleArtsSkillSlots.COMBAT_ART, KeyBinds.USE_ART_1);
					}
				}
			}
		}
		while (KeyBinds.USE_TAUNT.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != KeyBinds.USE_TAUNT)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(KeyBinds.USE_TAUNT.getKey()))
				{
					SkillContainer skill = this.playerpatch.getSkill(BattleArtsSkillSlots.TAUNT);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(BattleArtsSkillSlots.TAUNT, KeyBinds.USE_TAUNT);
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
					SkillContainer skill = this.playerpatch.getSkill(BattleArtsSkillSlots.ULTIMATE_ART);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(BattleArtsSkillSlots.ULTIMATE_ART, KeyBinds.USE_ULTIMATE_ART);
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
					SkillContainer skill = this.playerpatch.getSkill(BattleArtsSkillSlots.BURST_ART);
					if (epic_fight_battle_styles$executeRequest(skill))
					{
						this.reserveKey(BattleArtsSkillSlots.BURST_ART, KeyBinds.USE_BURST_ART);
					}
				}
			}
		}

		while (EpicFightKeyMappings.ATTACK.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != EpicFightKeyMappings.ATTACK)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getKey()))
				{

					SkillSlot slot;
					if (playerpatch.getSkill(SkillSlots.AIR_ATTACK).getSkill() instanceof MidAirAttack)
						slot = (AnimationHelpers.isInAir(this.playerpatch)) ? SkillSlots.AIR_ATTACK : SkillSlots.BASIC_ATTACK;
					else
						slot = (!this.player.onGround() && !this.player.isInWater() && this.player.getDeltaMovement().y > 0.05D) ? SkillSlots.AIR_ATTACK : SkillSlots.BASIC_ATTACK;

					if (this.playerpatch.getSkill(slot).sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).isExecutable())
					{
						this.player.resetAttackStrengthTicker();
						this.attackLightPressToggle = false;
						this.releaseAllServedKeys();
					} else
					{
						if (!this.player.isSpectator() && slot == SkillSlots.BASIC_ATTACK)
						{
							this.reserveKey(slot, EpicFightKeyMappings.ATTACK);
						}
					}

					this.lockHotkeys();
					this.attackLightPressToggle = false;
					this.weaponInnatePressToggle = false;
					this.weaponInnatePressCounter = 0;
				} else
				{
					if (!this.weaponInnatePressToggle)
					{
						this.weaponInnatePressToggle = true;
					}
				}

				//Disable vanilla attack
				if (this.options.keyAttack.getKey() == EpicFightKeyMappings.ATTACK.getKey())
				{
					ControllEngine.disableKey(this.options.keyAttack);
				}
			}
		}

		while (EpicFightKeyMappings.DODGE.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != EpicFightKeyMappings.DODGE)
			{
				if (EpicFightKeyMappings.DODGE.getKey().getValue() == this.options.keyShift.getKey().getValue())
				{
					if (this.player.getVehicle() == null)
					{
						if (!this.sneakPressToggle)
						{
							this.sneakPressToggle = true;
						}
					}
				} else
				{
					SkillSlot skillCategory = (this.playerpatch.getEntityState().knockDown()) ? SkillSlots.KNOCKDOWN_WAKEUP : SkillSlots.DODGE;
					SkillContainer skill = this.playerpatch.getSkill(skillCategory);

					if (skill.sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).shouldReserverKey())
					{
						this.reserveKey(SkillSlots.DODGE, EpicFightKeyMappings.DODGE);
					}
				}
			}
		}

		while (EpicFightKeyMappings.GUARD.consumeClick())
		{
		}

		while (EpicFightKeyMappings.WEAPON_INNATE_SKILL.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && this.currentChargingKey != EpicFightKeyMappings.WEAPON_INNATE_SKILL)
			{
				if (!EpicFightKeyMappings.ATTACK.getKey().equals(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getKey()))
				{
					if (this.playerpatch.getSkill(SkillSlots.WEAPON_INNATE).sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).shouldReserverKey())
					{
						if (!this.player.isSpectator())
						{
							this.reserveKey(SkillSlots.WEAPON_INNATE, EpicFightKeyMappings.WEAPON_INNATE_SKILL);
						}
					} else
					{
						this.lockHotkeys();
					}
				}
			}
		}

		while (EpicFightKeyMappings.MOVER_SKILL.consumeClick())
		{
			if (this.playerpatch.isBattleMode() && !this.playerpatch.isChargingSkill())
			{
				if (EpicFightKeyMappings.MOVER_SKILL.getKey().getValue() == this.options.keyJump.getKey().getValue())
				{
					SkillContainer skillContainer = this.playerpatch.getSkill(SkillSlots.MOVER);
					SkillExecuteEvent event = new SkillExecuteEvent(this.playerpatch, skillContainer);

					if (skillContainer.canExecute(playerpatch, event) && this.player.getVehicle() == null)
					{
						if (!this.moverPressToggle)
						{
							this.moverPressToggle = true;
						}
					}
				} else
				{
					SkillContainer skill = this.playerpatch.getSkill(SkillSlots.MOVER);
					skill.sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine);
				}
			}
		}

		while (EpicFightKeyMappings.SWITCH_MODE.consumeClick())
		{
			if (this.playerpatch.getOriginal().level().getGameRules().getBoolean(EpicFightGamerules.CAN_SWITCH_COMBAT))
			{
				this.playerpatch.toggleMode();
			}
		}

		while (EpicFightKeyMappings.LOCK_ON.consumeClick())
		{
			this.playerpatch.toggleLockOn();
		}

		//Disable swap hand items
		if (this.playerpatch.getEntityState().inaction() || (!this.playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).canBePlacedOffhand()))
		{
			ControllEngine.disableKey(this.minecraft.options.keySwapOffhand);
		}

		this.tick();
	}

	/**
	 * @author
	 * @reason
	 */
	@Overwrite
	private void tick() {
		if (this.playerpatch == null || !this.playerpatch.isBattleMode() || Minecraft.getInstance().isPaused()) {
			return;
		}

		if (this.player.tickCount - this.lastHotbarLockedTime > 20 && this.hotbarLocked) {
			this.unlockHotkeys();
		}

		if (this.weaponInnatePressToggle) {
			if (!ControllEngine.isKeyDown(EpicFightKeyMappings.WEAPON_INNATE_SKILL)) {
				this.attackLightPressToggle = true;
				this.weaponInnatePressToggle = false;
				this.weaponInnatePressCounter = 0;
			} else {
				if (EpicFightKeyMappings.WEAPON_INNATE_SKILL.getKey().equals(EpicFightKeyMappings.ATTACK.getKey())) {
					if (this.weaponInnatePressCounter > EpicFightMod.CLIENT_CONFIGS.longPressCount.getValue()) {
						if (this.minecraft.hitResult.getType() == HitResult.Type.BLOCK && this.playerpatch.getTarget() == null && !EpicFightMod.CLIENT_CONFIGS.noMiningInCombat.getValue()) {
							this.minecraft.startAttack();
							ControllEngine.setKeyBind(EpicFightKeyMappings.ATTACK, true);
						} else if (this.playerpatch.getSkill(SkillSlots.WEAPON_INNATE).sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).shouldReserverKey()) {
							if (!this.player.isSpectator()) {
								this.reserveKey(SkillSlots.WEAPON_INNATE, EpicFightKeyMappings.WEAPON_INNATE_SKILL);
							}
						} else {
							this.lockHotkeys();
						}

						this.weaponInnatePressToggle = false;
						this.weaponInnatePressCounter = 0;
					} else {
						this.weaponInnatePressCounter++;
					}
				}
			}
		}

		if (this.attackLightPressToggle)
		{
			SkillSlot slot;
			if (playerpatch.getSkill(SkillSlots.AIR_ATTACK).getSkill() instanceof MidAirAttack)
			    slot = (AnimationHelpers.isInAir(playerpatch)) ? SkillSlots.AIR_ATTACK : SkillSlots.BASIC_ATTACK;
			else
				slot = (!this.player.onGround() && !this.player.isInWater() && this.player.getDeltaMovement().y > 0.05D) ? SkillSlots.AIR_ATTACK : SkillSlots.BASIC_ATTACK;

			if (this.playerpatch.getSkill(slot).sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).isExecutable()) {
				this.player.resetAttackStrengthTicker();
				this.releaseAllServedKeys();
			} else {
				if (!this.player.isSpectator() && slot == SkillSlots.BASIC_ATTACK) {
					this.reserveKey(slot, EpicFightKeyMappings.ATTACK);
				}
			}

			this.lockHotkeys();

			this.attackLightPressToggle = false;
			this.weaponInnatePressToggle = false;
			this.weaponInnatePressCounter = 0;
		}

		if (this.sneakPressToggle) {
			if (!ControllEngine.isKeyDown(this.options.keyShift)) {
				SkillSlot skillSlot = (this.playerpatch.getEntityState().knockDown()) ? SkillSlots.KNOCKDOWN_WAKEUP : SkillSlots.DODGE;
				SkillContainer skill = this.playerpatch.getSkill(skillSlot);

				if (skill.sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).shouldReserverKey()) {
					this.reserveKey(skillSlot, this.options.keyShift);
				}

				this.sneakPressToggle = false;
				this.sneakPressCounter = 0;
			} else {
				if (this.sneakPressCounter > EpicFightMod.CLIENT_CONFIGS.longPressCount.getValue()) {
					this.sneakPressToggle = false;
					this.sneakPressCounter = 0;
				} else {
					this.sneakPressCounter++;
				}
			}
		}

		if (this.currentChargingKey != null) {
			SkillContainer skill = this.playerpatch.getSkill(this.reservedOrChargingSkillSlot);

			if (skill.getSkill() instanceof ChargeableSkill chargingSkill) {
				if (!ControllEngine.isKeyDown(this.currentChargingKey)) {
					this.chargeKeyUnpressed = true;
				}

				if (this.chargeKeyUnpressed) {
					if (this.playerpatch.getSkillChargingTicks() > chargingSkill.getMinChargingTicks()) {
						if (skill.getSkill() != null) {
							skill.sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine);
						}

						this.releaseAllServedKeys();
					}
				}

				if (this.playerpatch.getSkillChargingTicks() >= chargingSkill.getAllowedMaxChargingTicks()) {
					this.releaseAllServedKeys();
				}
			} else {
				this.releaseAllServedKeys();
			}
		}

		if (this.reservedKey != null) {
			if (this.reserveCounter > 0) {
				SkillContainer skill = this.playerpatch.getSkill(this.reservedOrChargingSkillSlot);
				this.reserveCounter--;

				if (skill.getSkill() != null) {
					if (skill.sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).isExecutable()) {
						this.releaseAllServedKeys();
						this.lockHotkeys();
					}
				}
			} else {
				this.releaseAllServedKeys();
			}
		}

		if (this.playerpatch.getEntityState().inaction() || this.hotbarLocked) {
			for (int i = 0; i < 9; ++i) {
				while (this.options.keyHotbarSlots[i].consumeClick());
			}

			while (this.options.keyDrop.consumeClick());
		}
	}

	@Unique
	private boolean epic_fight_battle_styles$executeRequest(SkillContainer skill) {
		return skill.sendExecuteRequest(this.playerpatch, epic_fight_battle_styles$controlEngine).shouldReserverKey();
	}
}
