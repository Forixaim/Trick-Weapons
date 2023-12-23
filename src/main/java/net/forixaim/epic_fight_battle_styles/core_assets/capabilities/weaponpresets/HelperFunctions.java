package net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public class HelperFunctions
{
	//Initial Helper Functions
	public static boolean offHandItem(LivingEntityPatch<?> entityPatch, WeaponCategory category)
	{
		return entityPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == category;
	}
	public static boolean skillCheck(LivingEntityPatch<?> entityPatch, Skill skill)
	{
		if (entityPatch instanceof PlayerPatch)
		{
			return ((PlayerPatch<?>) entityPatch).getSkill(skill) != null;
		}
		return false;
	}
}
