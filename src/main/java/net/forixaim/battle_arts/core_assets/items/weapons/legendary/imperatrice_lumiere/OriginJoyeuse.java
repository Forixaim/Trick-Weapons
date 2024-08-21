package net.forixaim.battle_arts.core_assets.items.weapons.legendary.imperatrice_lumiere;

import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.items.BattleArtsRarities;
import net.forixaim.battle_arts.core_assets.items.EpicFightBattleStylesTiers;
import net.forixaim.battle_arts.core_assets.items.weapons.legendary.LegendaryWeapon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import yesman.epicfight.world.item.WeaponItem;

import javax.annotation.Nullable;
import java.util.List;

public class OriginJoyeuse extends WeaponItem
{
	public OriginJoyeuse()
	{
		super(EpicFightBattleStylesTiers.ORIGIN_JOYEUSE, 5, -3f, new Properties().durability(0).defaultDurability(0).rarity(BattleArtsRarities.IMPERATRICE_LUMIERE));
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.add(Component.literal(""));
		tooltip.add(Component.translatable("item." + EpicFightBattleArts.MOD_ID + ".origin_joyeuse.tooltip").withStyle(ChatFormatting.DARK_RED));
	}
}
