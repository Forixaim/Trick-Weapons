package net.forixaim.epic_fight_battle_styles.core_assets.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.item.SkillBookItem;

import javax.annotation.Nullable;
import java.util.List;

public class BattleStyleBookItem extends SkillBookItem
{

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (stack.getTag() != null && stack.getTag().contains("skill")) {
			ResourceLocation rl = new ResourceLocation(stack.getTag().getString("skill"));
			tooltip.add(Component.translatable(String.format("skill.%s.%s", rl.getNamespace(), rl.getPath())).withStyle(ChatFormatting.GOLD));
		}
	}
	public BattleStyleBookItem(Properties properties) {
		super(properties);
	}
}
