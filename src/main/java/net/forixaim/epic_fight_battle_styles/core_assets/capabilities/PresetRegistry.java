package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HouseLuxAMStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.hybrid.Chakram;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.function.Function;

//This is where all the weapon capability presets are implemented
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PresetRegistry
{
	public static final Function<Item, CapabilityItem.Builder> RAPIER = (item) ->
		EFBSWeaponCapability.builder()
			.redirectedCategory(CapabilityItem.WeaponCategories.SWORD)
			.redirectedProvider(Rapier.styleProvider)
				.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
			.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Rapier.defaultOneHandAttack)
			.redirectedCollider(ColliderPreset.LONGSWORD);

	public static final Function<Item, CapabilityItem.Builder> GREATSWORD = (item) ->
			EFBSWeaponCapability.builder()
					.redirectedCategory(CapabilityItem.WeaponCategories.GREATSWORD)
					.redirectedCollider(ColliderPreset.GREATSWORD)
					.redirectedSwingSound(EpicFightSounds.WHOOSH_BIG.get())
					.redirectedProvider(Greatsword.styleProvider)
					.createStyleCategory(HouseLuxAMStyles.HLAM_GREATSWORD_EXCALIBUR, Greatsword.houseLuxAM)
					.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Greatsword.defaultGS);

	public static final Function<Item, CapabilityItem.Builder> CHAKRAM = (item) ->
		EFBSWeaponCapability.builder()
			.redirectedProvider(Chakram.styleProvider)
			.redirectedCategory(BattleStyleCategories.CHAKRAM)
			.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
			.redirectedSwingSound(EpicFightSounds.WHOOSH_SMALL.get())
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Chakram.defaultOneHandAttackCycle)
			.weaponCombinationPredicator(Chakram.comboPredicator);

	public static final Function<Item, CapabilityItem.Builder> SWORD = (item) ->
		EFBSWeaponCapability.builder()
			.redirectedCategory(CapabilityItem.WeaponCategories.SWORD)
			.redirectedProvider(Sword.styleProvider)
			.redirectedCollider(ColliderPreset.SWORD)
			.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
			.redirectedSwingSound(EpicFightSounds.WHOOSH.get())
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Sword.defaultOneHandAttackCycle)
			.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Sword.defaultTwoHandAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD, Sword.heroSwordAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD_SHIELD, Sword.heroSwordShieldAttackCycle)
			.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
			.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.weaponCombinationPredicator(Sword.comboPredicator);



	public static final Function<Item, CapabilityItem.Builder> LONGSWORD = (item) ->
		EFBSWeaponCapability.builder()
			.redirectedCategory(CapabilityItem.WeaponCategories.LONGSWORD)
			.redirectedCollider(ColliderPreset.LONGSWORD)
			.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
			.redirectedProvider(Longsword.styleProvider)
			.redirectedSwingSound(EpicFightSounds.WHOOSH.get())
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Longsword.defaultOneHandAttackCycle)
			.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Longsword.defaultTwoHandAttackCycle)
			.createStyleCategory(CapabilityItem.Styles.OCHS, Longsword.LiechtenauerAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD, Longsword.heroSwordAttackCycle)
			.createStyleCategory(HeroStyles.HERO_SWORD_SHIELD, Longsword.heroSwordShieldAttackCycle)
			.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
			.canBePlacedOffhand(false)
				.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.weaponCombinationPredicator(Longsword.comboPredicator);
	public static final Function<Item, CapabilityItem.Builder> TACHI = (item) ->
			EFBSWeaponCapability.builder()
					.redirectedCategory(CapabilityItem.WeaponCategories.TACHI)
					.redirectedCollider(ColliderPreset.TACHI)
					.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
					.redirectedSwingSound(EpicFightSounds.WHOOSH.get())
					.redirectedProvider(Tachi.styleProvider)
					.createStyleCategory(CapabilityItem.Styles.TWO_HAND, Tachi.defaultTachiAttack)
					.createStyleCategory(ImperatriceLumiereStyles.IMPERATRICE_SWORD, Sword.imperatriceLumiere)
					.canBePlacedOffhand(false)
					.newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK);


	@SubscribeEvent
	public static void Register(WeaponCapabilityPresetRegistryEvent Event)
	{
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "chakram"), CHAKRAM);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "sword"), SWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "longsword"), LONGSWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "rapier"), RAPIER);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "greatsword"), GREATSWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "tachi"), TACHI);
	}
}
