package net.forixaim.battle_arts.core_assets.entity.friendly_npcs;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.level.Level;

public abstract class AbstractFriendlyNPC extends PathfinderMob implements Npc
{
	protected AbstractFriendlyNPC(EntityType<? extends PathfinderMob> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
	}
}
