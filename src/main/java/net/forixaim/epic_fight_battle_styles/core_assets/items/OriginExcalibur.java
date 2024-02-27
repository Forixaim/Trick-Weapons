package net.forixaim.epic_fight_battle_styles.core_assets.items;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
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

public class OriginExcalibur extends WeaponItem
{
	@OnlyIn(Dist.CLIENT)
	private List<Component> tooltip;
	public OriginExcalibur()
	{
		super(EpicFightBattleStylesTiers.ORIGIN_EXCALIBUR, 9, -3f, new Properties().durability(0).defaultDurability(0));
		if (EpicFightMod.isPhysicalClient())
		{
			this.tooltip = new ArrayList<Component>();
			this.tooltip.add(Component.literal(""));
			this.tooltip.add(Component.translatable("item." + EpicFightBattleStyles.MOD_ID + ".origin_excalibur.tooltip"));
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
		tooltip.addAll(tooltip);
	}
}
