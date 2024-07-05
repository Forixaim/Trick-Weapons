package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HeroStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.HouseLuxAMStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.styles.ImperatriceLumiereStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.hybrid.Chakram;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.*;
import net.forixaim.epic_fight_battle_styles.core_assets.capabilities.weaponpresets.melee.legendary.Joyeuse;
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
	public static final Function<Item, CapabilityItem.Builder> GREATSWORD = (item) ->
			Greatsword.getBuilder();


	public static final Function<Item, CapabilityItem.Builder> CHAKRAM = (item) ->
		EFBSWeaponCapability.builder()
			.redirectedProvider(Chakram.styleProvider)
			.redirectedCategory(BattleStyleCategories.CHAKRAM)
			.redirectedHitSound(EpicFightSounds.BLADE_HIT.get())
			.redirectedSwingSound(EpicFightSounds.WHOOSH_SMALL.get())
			.createStyleCategory(CapabilityItem.Styles.ONE_HAND, Chakram.defaultOneHandAttackCycle)
			.weaponCombinationPredicator(Chakram.comboPredicator);

	public static final Function<Item, CapabilityItem.Builder> SWORD = (item) ->
			Sword.getBuilder();

	public static final Function<Item, CapabilityItem.Builder> LONGSWORD = (item) ->
			Longsword.getBuilder();

	public static final Function<Item, CapabilityItem.Builder> JOYEUSE = (item) ->
			Joyeuse.getBuilder();

	public static final Function<Item, CapabilityItem.Builder> TACHI = (item) ->
			Tachi.getBuilder();


	@SubscribeEvent
	public static void Register(WeaponCapabilityPresetRegistryEvent Event)
	{
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "chakram"), CHAKRAM);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "sword"), SWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "longsword"), LONGSWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "greatsword"), GREATSWORD);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "tachi"), TACHI);
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "joyeuse"), JOYEUSE);
	}
}
