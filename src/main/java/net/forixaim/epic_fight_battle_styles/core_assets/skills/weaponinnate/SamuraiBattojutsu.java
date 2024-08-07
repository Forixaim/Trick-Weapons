package net.forixaim.epic_fight_battle_styles.core_assets.skills.weaponinnate;

import net.forixaim.epic_fight_battle_styles.core_assets.skills.EFBSDataKeys;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillSlots;
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
		if (executer.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().hasData(EFBSDataKeys.BATTO_SHEATH.get()))
		{
			boolean isSheathed = executer.getSkill(EpicFightBattleStyleSkillSlots.BATTLE_STYLE).getDataManager().getDataValue(EFBSDataKeys.BATTO_SHEATH.get());

			if (isSheathed) {
				executer.playAnimationSynchronized(this.attackAnimations[this.getAnimationInCondition(executer)].get(), -0.65F);
			} else {
				executer.playAnimationSynchronized(this.attackAnimations[this.getAnimationInCondition(executer)].get(), 0);
			}
		}

	}
}
