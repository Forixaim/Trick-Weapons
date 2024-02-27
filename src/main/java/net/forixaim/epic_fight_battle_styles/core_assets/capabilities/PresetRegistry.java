package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import com.ibm.icu.impl.coll.CollationLoader;
import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleAnimations;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLuminelleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.hybrid.Chakram;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Longsword;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Rapier;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.Sword;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

//This is where all the weapon capability presets are implemented
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PresetRegistry
{
	public static final Function<Item, CapabilityItem.Builder> RAPIER = (item) ->
		EFBSWeaponCapability.builder()
			.redirectedCategory(CapabilityItem.WeaponCategories.SWORD)
			.redirectedProvider(Rapier.styleProvider)
			.createStyleCategory(ImperatriceLuminelleStyles.SWORD, Sword.imperatriceLumiere)
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Rapier.defaultOneHandAttack)
			.redirectedCollider(ColliderPreset.LONGSWORD);


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
			.createStyleCategory(ImperatriceLuminelleStyles.SWORD, Sword.imperatriceLumiere)
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
			.createStyleCategory(ImperatriceLuminelleStyles.SWORD, Sword.imperatriceLumiere)
			.canBePlacedOffhand(false)
			.weaponCombinationPredicator(Longsword.comboPredicator);


	@SubscribeEvent
	public static void Register(WeaponCapabilityPresetRegistryEvent Event)
	{
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "chakram"), CHAKRAM);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "sword"), SWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "longsword"), LONGSWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "rapier"), RAPIER);
	}
}
