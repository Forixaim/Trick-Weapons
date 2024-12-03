package net.forixaim.battle_arts.core_assets.animations.battle_style.novice.squire;

import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class SquireAnimations
{

	public static String squireAnimationPath(WeaponCategory category, String entry)
	{
		return "battle_style/novice/squire/" + category.toString().toLowerCase() + "/" + entry;
	}

	public static void Build()
	{
		SquireSwordAnimations.Build();
	}
}
