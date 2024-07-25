package net.forixaim.epic_fight_battle_styles;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = EpicFightBattleStyles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue TRIGGER_ANTI_CHEESE = BUILDER
            .comment("Whether to have Legendary Battle Styles instantly break the unfair armor anyone who wears it. (MekaSuit, Draconic Armor, ProjectE Armors, Infinity Armor, etc.)")
            .define("legendary_battle_style_can_puncture_invincibility", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean triggerAntiCheese;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        triggerAntiCheese = TRIGGER_ANTI_CHEESE.get();
    }
}
