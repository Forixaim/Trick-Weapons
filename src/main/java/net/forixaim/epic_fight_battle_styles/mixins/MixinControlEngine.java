package net.forixaim.epic_fight_battle_styles.mixins;

import com.mojang.logging.LogUtils;
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
import yesman.epicfight.skill.SkillSlots;

import java.util.Map;
import java.util.function.BiFunction;

@Mixin(value = ControllEngine.class)
public abstract class MixinControlEngine
{

	@Shadow @Final private Map<KeyMapping, BiFunction<KeyMapping, Integer, Boolean>> keyFunctions;

	@Shadow private LocalPlayerPatch playerpatch;

	@Shadow private KeyMapping currentChargingKey;

	@Shadow public Options options;

	@Shadow private LocalPlayer player;

	@Shadow private boolean sneakPressToggle;

	@Shadow protected abstract void reserveKey(SkillSlot slot, KeyMapping keyMapping);

	@Shadow public abstract void lockHotkeys();

	@Inject(method = "<init>", at = @At("RETURN"))
	public void constructorHead(CallbackInfo ci)
	{
		this.keyFunctions.put(KeyBinds.USE_ART_1, this::epic_fight_battle_styles$combatArt1Invoke);
	}

	@Unique
	private boolean epic_fight_battle_styles$combatArt1Invoke(KeyMapping key, int action) {
		if (action == 1 && this.playerpatch.isBattleMode() && this.currentChargingKey != key) {
			if (!EpicFightKeyMappings.ATTACK.getKey().equals(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getKey())) {
				if (this.playerpatch.getSkill(SkillSlots.WEAPON_INNATE).sendExecuteRequest(this.playerpatch, (ControllEngine) (Object) this).shouldReserverKey()) {
					if (!this.player.isSpectator()) {
						this.reserveKey(EpicFightBattleStyleSkillSlots.COMBAT_ART_1, key);
					}
				} else {
					this.lockHotkeys();
				}
			}
		}

		return false;
	}

	@Unique
	private boolean epic_fight_battle_styles$TestMethod(KeyMapping key, Integer x)
	{
		LogUtils.getLogger().info("Epic Fight Battle Styles Combat Art Mixin: Test Success");
		return false;
	}
}
