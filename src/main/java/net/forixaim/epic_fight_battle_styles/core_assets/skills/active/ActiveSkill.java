package net.forixaim.epic_fight_battle_styles.core_assets.skills.active;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.util.ManaUtil;
import com.mna.api.faction.FactionIDs;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.EpicFightBattleStyleSkillCategories;
import net.forixaim.epic_fight_battle_styles.core_assets.skills.battlestyle.BattleStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.NotImplementedException;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is not supposed to be used, all class builders will exist within their own sub abstract classes.
 */
public class ActiveSkill extends Skill
{
	protected List<WeaponCategory> allowedWeapons = Lists.newArrayList();

	protected List<Map<AnimationProperty.AttackPhaseProperty<?>, Object>> properties;

	public ActiveSkill(Builder<? extends Skill> builder) {
		super(builder);

		this.properties = Lists.newArrayList();
	}

	protected boolean weaponCategoryMatch(WeaponCategory category)
	{
		for (WeaponCategory category1 : allowedWeapons)
		{
			if (category1 == category)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canExecute(PlayerPatch<?> executer) {
		ItemStack weapon = executer.getOriginal().getMainHandItem();
		WeaponCategory weaponCategory = EpicFightCapabilities.getItemStackCapability(weapon).getWeaponCategory();
		if (executer.isLogicalClient())
		{
			return super.canExecute(executer);

		} else {
			ItemStack itemstack = executer.getOriginal().getMainHandItem();

			return super.canExecute(executer) && weaponCategoryMatch(weaponCategory)
					&& executer.getOriginal().getVehicle() == null && (!executer.getSkill(this).isActivated() || this.activateType == ActivateType.TOGGLE);
		}
	}


	@SuppressWarnings("unchecked")
	protected <V> Optional<V> getProperty(AnimationProperty.AttackPhaseProperty<V> propertyKey, Map<AnimationProperty.AttackPhaseProperty<?>, Object> map) {
		return (Optional<V>) Optional.ofNullable(map.get(propertyKey));
	}

	public ActiveSkill newProperty() {
		this.properties.add(Maps.newHashMap());

		return this;
	}

	public <T> ActiveSkill addProperty(AnimationProperty.AttackPhaseProperty<T> propertyKey, T object) {
		this.properties.get(properties.size() - 1).put(propertyKey, object);

		return this;
	}
}
