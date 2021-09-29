package com.example.fishfriend.core.init;

import com.example.fishfriend.FishFriend;
import com.example.fishfriend.entity.passive.FishEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			FishFriend.MOD_ID);

	//https://github.com/Silverminer007/MoreOre-1.16/blob/master/src/main/java/com/silverminer/moreore/init/ModEntityTypesInit.java
	public static final RegistryObject<EntityType<FishEntity>> FISH = ENTITY_TYPES.register("fish",
			() -> EntityType.Builder.<FishEntity>create(FishEntity::new, EntityClassification.WATER_AMBIENT)
					.size(0.5F, 0.3F).trackingRange(4).build(new ResourceLocation(FishFriend.MOD_ID, "textures/entity/fish.png").toString()));

	// public static final EntityType<CodEntity> COD = register("cod",
	// EntityType.Builder.<CodEntity>create(CodEntity::new,
	// EntityClassification.WATER_AMBIENT).size(0.5F, 0.3F).trackingRange(4));

	/*
	 * 
	 * public static final RegistryObject<EntityType<WoodWolfEntity>>
	 * WOODWOLF_ENTITY = ENTITY_TYPES .register("woodwolf", () ->
	 * EntityType.Builder.<WoodWolfEntity>create(WoodWolfEntity::new,
	 * EntityClassification.MISC) .size(0.6F, 0.85f) .build(new
	 * ResourceLocation(WoodWolf.MOD_ID, "woodwolf").toString()));
	 */
}
