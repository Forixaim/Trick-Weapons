package net.forixaim.epic_fight_battle_styles.core_assets.skills.active;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.common.capability.ManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.mna.api.faction.FactionIDs;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.progression.PlayerProgression;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import com.mna.loot.conditions.PlayerFactionCheck;
import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class is not supposed to be used, all class builders will exist within their own sub abstract classes.
 */
public abstract class ActiveSkill extends Skill
{
	//General Lists of allowed weapons and properties.
	protected List<WeaponCategory> allowedWeapons = Lists.newArrayList();
	protected List<Map<AnimationProperty.AttackPhaseProperty<?>, Object>> properties;

	//Resource Consumption
	protected float manaConsumption;
	protected float brimstoneConsumption;
	protected float soulConsumption;
	protected float staminaConsumption;

	public ActiveSkill(Builder<? extends Skill> builder) {
		super(builder);

		this.properties = Lists.newArrayList();
	}

	@Override
	public void setParams(CompoundTag parameters)
	{
		super.setParams(parameters);
		this.manaConsumption = parameters.getFloat("mana_consumption");
		this.staminaConsumption = parameters.getFloat("stamina_consumption");
	}

	private boolean manaCheck(PlayerPatch<?> executor)
	{
		if (ModList.get().isLoaded(IronsSpellbooks.MODID))
		{
			return MagicData.getPlayerMagicData(executor.getOriginal()).getMana() >= manaConsumption;
		}
		else
		{
			return executor.getStamina() >= (Math.round(manaConsumption/10) + staminaConsumption);
		}
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

			return super.canExecute(executer) && weaponCategoryMatch(weaponCategory) && manaCheck(executer)
					&& executer.getOriginal().getVehicle() == null && (!executer.getSkill(this).isActivated() || this.activateType == ActivateType.TOGGLE);
		}
	}

	@Override
	public void executeOnServer(ServerPlayerPatch executor, FriendlyByteBuf args)
	{
		if (ModList.get().isLoaded(IronsSpellbooks.MODID))
		{
			MagicData.getPlayerMagicData(executor.getOriginal()).addMana(-Math.round(manaConsumption/10));
		}
		else
		{
			executor.consumeStamina(Math.round(manaConsumption / 100));
		}
		executor.consumeStamina(staminaConsumption);

		super.executeOnServer(executor, args);
	}

	@Override
	public void onInitiate(SkillContainer container) {
		super.onInitiate(container);
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
