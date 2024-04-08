package net.forixaim.epic_fight_battle_styles.core_assets.skills;

import net.forixaim.epic_fight_battle_styles.EpicFightBattleStyles;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.skill.SkillDataKey;

import java.util.function.Supplier;

public class EFBSDataKeys
{
    private static final Supplier<RegistryBuilder<SkillDataKey<?>>> BUILDER = () -> new RegistryBuilder<SkillDataKey<?>>().addCallback(SkillDataKey.getCallBack());

    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation(EpicFightBattleStyles.MOD_ID, "data_keys"), EpicFightBattleStyles.MOD_ID);
    public static final Supplier<IForgeRegistry<SkillDataKey<?>>> REGISTRY = DATA_KEYS.makeRegistry(BUILDER);

    public static final RegistryObject<SkillDataKey<Float>> ULTIMATE_GAUGE = DATA_KEYS.register("ultimate_gauge", () -> SkillDataKey.createFloatKey(0.0F, true));
}
