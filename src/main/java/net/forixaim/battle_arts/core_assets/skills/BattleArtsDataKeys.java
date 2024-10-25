package net.forixaim.battle_arts.core_assets.skills;

import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.skills.battlestyle.common.advanced.Ronin;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.skill.SkillDataKey;

public class BattleArtsDataKeys
{
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation("epicfight", "skill_data_keys"), EpicFightBattleArts.MOD_ID);
    public static final RegistryObject<SkillDataKey<Boolean>> BATTO_SHEATH;

    static
    {
        BATTO_SHEATH = DATA_KEYS.register("batto_sheath", () -> SkillDataKey.createBooleanKey(false, true, Ronin.class));
    }
}
