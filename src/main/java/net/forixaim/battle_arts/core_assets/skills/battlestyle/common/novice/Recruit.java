package net.forixaim.battle_arts.core_assets.skills.battlestyle.common.novice;

import net.forixaim.battle_arts.core_assets.capabilities.styles.battle_style.novice.RecruitWieldStyles;
import net.forixaim.battle_arts.core_assets.skills.BattleArtsDataKeys;
import net.forixaim.battle_arts.core_assets.skills.weaponinnate.IronFortress;
import net.forixaim.bs_api.battle_arts_skills.battle_style.BattleStyle;
import net.forixaim.bs_api.proficiencies.Proficiencies;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class Recruit extends BattleStyle
{
	public static Skill IRON_FORTRESS;

	private static final UUID ID = UUID.fromString("ef4b6082-30a8-49cc-9a86-fa53e811210e");
	public Recruit(Builder<?> builder)
	{
		super(builder);
		innateInactiveColor = new float[]{0.271f, 0.212f, 0.133f};
		innateSkillColor = new float[]{1f, 0.561f, 0f};
		proficiencySpecialization.add(Proficiencies.POLEARMS);
	}


	public static void RegisterInnates(SkillBuildEvent.ModRegistryWorker worker)
	{
		IRON_FORTRESS = worker.build("iron_fortress", IronFortress::new, IronFortress.createWeaponInnateBuilder().setActivateType(ActivateType.DURATION)).newProperty();
	}

	@Override
	public SkillDataKey<Boolean> getSneakIsDisabledKey()
	{
		return BattleArtsDataKeys.SNEAK_MOVE_LOCK.get();
	}

	@Override
	public void onInitiate(SkillContainer container)
	{
		super.onInitiate(container);

		container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, ID, (event) ->
		{
			if (container.getExecuter().getOriginal().isShiftKeyDown())
			{
				if (event.getPlayerPatch().getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(event.getPlayerPatch()) == RecruitWieldStyles.RECRUIT_SPEAR)
					container.getDataManager().setDataSync(getSneakIsDisabledKey(), true, event.getPlayerPatch().getOriginal());
			}
		});
	}

	@Override
	public void onRemoved(SkillContainer container)
	{
		super.onRemoved(container);
		container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.MOVEMENT_INPUT_EVENT, ID);
	}

	@Override
	public void updateContainer(SkillContainer container)
	{
		super.updateContainer(container);
		if (!container.getExecuter().getOriginal().isShiftKeyDown() && container.getExecuter().isLogicalClient())
		{
			container.getDataManager().setDataSync(getSneakIsDisabledKey(), false, (LocalPlayer) container.getExecuter().getOriginal());
		}
	}
}
