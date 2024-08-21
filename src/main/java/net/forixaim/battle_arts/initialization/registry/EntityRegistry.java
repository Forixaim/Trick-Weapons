package net.forixaim.battle_arts.initialization.registry;

import net.forixaim.battle_arts.EpicFightBattleArts;
import net.forixaim.battle_arts.core_assets.entity.BattleArtsEntityCategories;
import net.forixaim.battle_arts.core_assets.entity.bosses.Charlemagne;
import net.forixaim.battle_arts.core_assets.entity.projectiles.IncandescentFirework;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import reascer.wom.world.entity.projectile.AntitheusDarkness;

public class EntityRegistry
{
	public static final DeferredRegister<EntityType<?>> ENTITIES;
	public static final RegistryObject<EntityType<Charlemagne>> CHARLEMAGNE;
	public static final RegistryObject<EntityType<IncandescentFirework>> INCANDESCENT_FIREWORK;

	public EntityRegistry()
	{

	}

	static
	{
		ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EpicFightBattleArts.MOD_ID);
		INCANDESCENT_FIREWORK = ENTITIES.register("incandescent_firework",
				() ->
				Builder.<IncandescentFirework>of(IncandescentFirework::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(12).updateInterval(20).build("incandescent_firework")
				);
		CHARLEMAGNE = ENTITIES.register("charlemagne",
				() -> Builder.of(Charlemagne::new, BattleArtsEntityCategories.FRIENDLY_NPC)
						.fireImmune()
						.sized(2f, 0.5f)
						.build("charlemagne"));

	}


}
