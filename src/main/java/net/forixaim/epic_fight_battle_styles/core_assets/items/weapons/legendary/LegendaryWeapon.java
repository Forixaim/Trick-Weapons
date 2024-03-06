package net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.item.WeaponItem;

import javax.annotation.Nullable;
import java.util.List;

public class LegendaryWeapon extends WeaponItem {
    public LegendaryWeapon(Tier tier, int damageIn, float speedIn) {
        super(tier, damageIn, speedIn, new Properties().durability(0).defaultDurability(0));
    }
}
