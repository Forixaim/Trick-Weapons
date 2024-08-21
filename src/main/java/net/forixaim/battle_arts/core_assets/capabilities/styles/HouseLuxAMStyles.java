package net.forixaim.battle_arts.core_assets.capabilities.styles;

import yesman.epicfight.world.capabilities.item.Style;

public enum HouseLuxAMStyles implements Style
{
    HLAM_SWORD(true),
    HLAM_GREATSWORD_EXCALIBUR(false);
    final boolean OffHandUse;
    final int id;
    HouseLuxAMStyles(boolean offHandUse)
    {
        this.id = ENUM_MANAGER.assign(this);
        this.OffHandUse = offHandUse;
    }
    @Override
    public boolean canUseOffhand() {
        return false;
    }

    @Override
    public int universalOrdinal() {
        return 0;
    }
}
