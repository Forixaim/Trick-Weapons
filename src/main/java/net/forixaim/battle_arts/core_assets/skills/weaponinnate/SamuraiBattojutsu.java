package net.forixaim.battle_arts.core_assets.skills.weaponinnate;

import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.forixaim.bs_api.battle_arts_skills.BattleArtsSkillSlots;
import yesman.epicfight.skill.weaponinnate.ConditionalWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class SamuraiBattojutsu extends ConditionalWeaponInnateSkill
{
	public SamuraiBattojutsu(ConditionalWeaponInnateSkill.Builder builder)
	{
		super(builder);
	}

	@Override
	public void playSkillAnimation(ServerPlayerPatch executer)
	{
		if (executer.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().hasData(BattleArtsDataKeys.BATTO_SHEATH.get()))
		{
			boolean isSheathed = executer.getSkill(BattleArtsSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(BattleArtsDataKeys.BATTO_SHEATH.get());

			if (isSheathed) {
				executer.playAnimationSynchronized(this.attackAnimations[this.getAnimationInCondition(executer)].get(), -0.65F);
			} else {
				executer.playAnimationSynchronized(this.attackAnimations[this.getAnimationInCondition(executer)].get(), 0);
			}
		}

	}
}
