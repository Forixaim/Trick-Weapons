package net.forixaim.battle_arts.core_assets.items.weapons.legendary;

import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

public class LegendaryWeapon extends WeaponItem {
    public LegendaryWeapon(Tier tier, int damageIn, float speedIn) {
        super(tier, damageIn, speedIn, new Properties().durability(0).defaultDurability(0));
    }
}
