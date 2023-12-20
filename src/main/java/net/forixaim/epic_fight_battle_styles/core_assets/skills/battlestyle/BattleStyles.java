package net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleCategories;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.utils.ParseUtil;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;

import java.util.Map;
import java.util.Set;

/**
 * This class extends the skill class and is mainly there as a framework for the Battle Styles which can modify certain things.
 */
public class BattleStyles extends Skill
{
	public static Skill.Builder<BattleStyles> CreateBattleStyle()
	{
		return (new Skill.Builder<BattleStyles>().setCategory(EpicFightBattleStyleCategories.BATTLE_STYLE).setResource(Resource.NONE));

	}
	private final Map<Attribute, AttributeModifier> BattleStyleStatModifier;

	public BattleStyles(Builder<? extends Skill> builder)
	{
		super (builder);
		this.BattleStyleStatModifier = Maps.newHashMap();
	}

	@Override
	public void setParams(CompoundTag parameters) {
		super.setParams(parameters);

		this.BattleStyleStatModifier.clear();

		if (parameters.contains("attribute_modifiers")) {
			ListTag attributeList = parameters.getList("attribute_modifiers", 10);

			for (Tag tag : attributeList) {
				CompoundTag comp = (CompoundTag)tag;
				String attribute = comp.getString("attribute");
				Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attribute));
				AttributeModifier modifier = ParseUtil.toAttributeModifier(comp);

				this.BattleStyleStatModifier.put(attr, modifier);
			}
		}
	}

	@Override
	public void onInitiate(SkillContainer container) {
		for (Map.Entry<Attribute, AttributeModifier> stat : this.BattleStyleStatModifier.entrySet()) {
			AttributeInstance attr = container.getExecuter().getOriginal().getAttribute(stat.getKey());

			if (!attr.hasModifier(stat.getValue())) {
				attr.addTransientModifier(stat.getValue());
			}
		}
	}

	@Override
	public void onRemoved(SkillContainer container) {
		for (Map.Entry<Attribute, AttributeModifier> stat : this.BattleStyleStatModifier.entrySet()) {
			AttributeInstance attr = container.getExecuter().getOriginal().getAttribute(stat.getKey());

			if (attr.hasModifier(stat.getValue())) {
				attr.removeModifier(stat.getValue());
			}
		}
	}

	public Set<Map.Entry<Attribute, AttributeModifier>> getModfierEntry() {
		return this.BattleStyleStatModifier.entrySet();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void drawOnGui(BattleModeGui gui, SkillContainer container, PoseStack poseStack, float x, float y) {
		poseStack.pushPose();
		poseStack.translate(0, (float)gui.getSlidingProgression(), 0);
		RenderSystem.setShaderTexture(0, this.getSkillTexture());
		GuiComponent.blit(poseStack, (int)x, (int)y, 24, 24, 0, 0, 1, 1, 1, 1);
		String remainTime = String.format("%.0f", container.getMaxResource() - container.getResource());
		gui.font.drawShadow(poseStack, remainTime, x + 12 - 4 * remainTime.length(), (y+6), 16777215);
		poseStack.popPose();
	}
}
