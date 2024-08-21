package net.forixaim.battle_arts;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = EpicFightBattleArts.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue TRIGGER_ANTI_CHEESE = BUILDER
            .comment("Whether to have Legendary Battle Styles instantly break the unfair armor anyone who wears it. (MekaSuit, Draconic Armor, ProjectE Armors, Infinity Armor, etc.)")
            .define("legendary_battle_style_can_puncture_invincibility", true);
    public static final ForgeConfigSpec.BooleanValue UNCONDITIONAL_RIPOSTE = BUILDER
            .comment("Whether to have Imperatrice Lumiere unconditionally riposte when executing a spot-dodge (Debug purposes only)")
            .define("unconditional_riposte", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean unconditionalRiposte;
    public static boolean triggerAntiCheese;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        triggerAntiCheese = TRIGGER_ANTI_CHEESE.get();
        unconditionalRiposte = UNCONDITIONAL_RIPOSTE.get();
    }
}
