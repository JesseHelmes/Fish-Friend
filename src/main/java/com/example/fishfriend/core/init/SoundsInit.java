package com.example.fishfriend.core.init;

import com.example.fishfriend.FishFriend;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundsInit {
	// to do sound subtitles in lang use "subtitles.entity.creeper.hurt": "Creeper
	// hurts", where anything after subtitles is the registry name
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			FishFriend.MOD_ID);

	// https://www.myinstants.com/instant/minecraft-hit-8123/
	// https://github.com/DaRealTurtyWurty/1.15-Tut-Mod/tree/master/src/main/resources/assets/tutorialmod/sounds/entity
	public static final RegistryObject<SoundEvent> FISH_ITEM_SLAP = SOUNDS.register("item.fish.slap",
			() -> new SoundEvent(new ResourceLocation(FishFriend.MOD_ID, "item.fish.slap")));
}
