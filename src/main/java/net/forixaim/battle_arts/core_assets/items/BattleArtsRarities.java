package net.forixaim.battle_arts.core_assets.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;

import java.util.function.UnaryOperator;

public class BattleArtsRarities
{
	public static UnaryOperator<Style> InfernalStyle = style -> {
		style.withColor(ChatFormatting.RED);
		style.withBold(true);
		return style;
	};


	public static final Rarity IMPERATRICE_LUMIERE = Rarity.create("IMPERATRICE_LUMIERE", ChatFormatting.RED);
	public static final Rarity HELIOLUX_KING = Rarity.create("KING_OF_SUNLIGHT", ChatFormatting.GOLD);
}
