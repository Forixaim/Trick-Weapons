package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import com.mojang.datafixers.util.Pair;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.WoMUniqueStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Sword;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.unique.Herrscher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMColliders;
import reascer.wom.gameasset.WOMSkills;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Function;

/**
 * This class only loads when WoM is loaded
 */
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WoMPresets
{
	private static Herrscher HERRSCHER_METHODS = null;
	public static final Function<Item, CapabilityItem.Builder> EFBS_HERRSCHER = (item) -> {
		CapabilityItem.Builder builder = EFBSWeaponCapability.builder()
				.redirectedCategory(CapabilityItem.WeaponCategories.SWORD)
				.redirectedProvider(HERRSCHER_METHODS.styleProvider)
				.redirectedCollider(WOMColliders.HERSCHER)
				.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
				.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Sword.defaultTwoHandAttackCycle)
				.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Sword.defaultOneHandAttackCycle)
				.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
				.createStyleCategory(WoMUniqueStyles.ATLANTEAN, HERRSCHER_METHODS.atlanteanAttackCycle)
				.comboCancel((style) -> false)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
				.weaponCombinationPredicator(HERRSCHER_METHODS.comboPredicator);

		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * harvestLevel)));
			builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
		}

		return builder;
	};
	public static void register(WeaponCapabilityPresetRegistryEvent event)
	{
		HERRSCHER_METHODS = new Herrscher();
		event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "efbs-herrscher"), EFBS_HERRSCHER);
	}
}
