package net.forixaim.epic_fight_battle_styles.core_assets.capabilities;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.BattleStylesAnimation;
import net.forixaim.epic_fight_battle_styles.core_assets.animations.ChakramAnimations;
import net.forixaim.epic_fight_battle_styles.initialization.registry.SkillRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

//This is where all the weapon capability presets are implemented
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Presets
{

	public static final Function<Item, CapabilityItem.Builder> CHAKRAM = (item) ->
			WeaponCapability.builder()
					.styleProvider(
							(playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == BattleStyleCategories.CHAKRAM ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
					.category(BattleStyleCategories.CHAKRAM)
					.hitSound(EpicFightSounds.BLADE_HIT.get())
					.innateSkill(CapabilityItem.Styles.ONE_HAND, (itemStack) -> SkillRegistry.PRECISION_VERTICAL)
					.swingSound(EpicFightSounds.WHOOSH_SMALL.get())
					.newStyleCombo(CapabilityItem.Styles.ONE_HAND,
							ChakramAnimations.SINGLE_CHAKRAM_AUTO_1,
							ChakramAnimations.SINGLE_CHAKRAM_AUTO_2,
							ChakramAnimations.SINGLE_CHAKRAM_DASH_ATTACK, ChakramAnimations.SINGLE_CHAKRAM_AIR_SLASH)
				.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, ChakramAnimations.SINGLE_CHAKRAM_IDLE)
					.livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, ChakramAnimations.SINGLE_CHAKRAM_WALK)
				.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == BattleStyleCategories.CHAKRAM);

	@SubscribeEvent
	public static void Register(WeaponCapabilityPresetRegistryEvent Event)
	{
		Event.getTypeEntry().put(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "chakram"), CHAKRAM);
	}
}
