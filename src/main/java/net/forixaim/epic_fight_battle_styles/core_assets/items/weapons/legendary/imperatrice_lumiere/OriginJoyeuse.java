package net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary.imperatrice_lumiere;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.items.EpicFightBattleStylesTiers;
import net.forixaim.epic_fight_battle_styles.core_assets.items.weapons.legendary.LegendaryWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class OriginJoyeuse extends LegendaryWeapon
{
	public OriginJoyeuse()
	{
		super(EpicFightBattleStylesTiers.ORIGIN_JOYEUSE, 5, -3f);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.add(Component.literal(""));
		tooltip.add(Component.translatable("item." + EpicFightBattleStyles.MOD_ID + ".origin_joyeuse.tooltip"));
	}
}
