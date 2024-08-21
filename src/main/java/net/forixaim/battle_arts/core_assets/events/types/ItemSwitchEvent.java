package net.forixaim.battle_arts.core_assets.events.types;

import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEvent;

public class ItemSwitchEvent<T extends PlayerPatch<?>> extends PlayerEvent<T>
{
	public ItemSwitchEvent(T playerPatch)
	{
		super(playerPatch, true);
	}
}
