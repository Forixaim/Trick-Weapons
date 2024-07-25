package net.forixaim.epic_fight_battle_styles.mixins.skills;


import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPChangeSkill;
import yesman.epicfight.network.server.SPChangeSkill;
import yesman.epicfight.network.server.SPDatapackSyncSkill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mixin(value = SkillManager.class, remap = false)
public abstract class MixinSkillManager
{
	@Inject(method = "processServerPacket", at = @At("RETURN"), remap = false)
	private static void ef_bs$processServerPacket(SPDatapackSyncSkill packet, final CallbackInfo ci)
	{
		LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();

		if (localPlayerPatch != null)
		{
			if (!localPlayerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).isEmpty() && localPlayerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getSkill() instanceof BattleStyle battleStyle)
			{
				if (battleStyle.getAssociatedBasicAttack() != null)
				{
					localPlayerPatch.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).sendExecuteRequest(localPlayerPatch, ClientEngine.getInstance().controllEngine);
				}
			}
		}
	}
}
