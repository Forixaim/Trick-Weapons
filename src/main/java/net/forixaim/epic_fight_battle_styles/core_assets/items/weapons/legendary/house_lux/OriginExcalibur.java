package net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary.house_lux;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.items.EpicFightBattleStylesTiers;
import net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary.LegendaryWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.item.WeaponItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class OriginExcalibur extends LegendaryWeapon
{
	public OriginExcalibur()
	{
		super(EpicFightBattleStylesTiers.ORIGIN_EXCALIBUR, 9, -3f);
	}

}
