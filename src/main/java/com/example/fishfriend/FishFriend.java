package com.example.fishfriend;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.fishfriend.client.entity.render.FishEntityRender;
import com.example.fishfriend.common.item.ModSpawnEggItem;
import com.example.fishfriend.core.init.EntityTypeInit;
import com.example.fishfriend.core.init.ItemInit;
import com.example.fishfriend.entity.passive.FishEntity;

// https://github.com/Silverminer007/MoreOre-1.16/blob/99ce3bd0ef8fb48ef394d841fb4bf138046e27c7/src/main/java/com/silverminer/moreore/util/CommonEvents.java#L112
/*
 * THE MAIN IDEA
 * 
 * Title: Fish is now a weapon?
Desc: kill someone with a fish
Set attack damage 
Dead dead bushes: black dead bush.
Fish friend
By silly Silverwing
You can use your fish as weapon when you hold it.
You can eat it and you will even get a advancement for it.
You can tame it like a dog.
It will fight with you if you are in the same water.
You can pick it up by right click.
And put it in water or ground by placing it.
It won't take damage from being out of the water if it has a owner.
You get horrible achievements for killing your fish friend.
It will swim like other fish, but then as a item.
Extra: fish slap sound when you hit an entity. 
 */
//use normal cod for now..
/*
 * 	ToDo
 * 
 * Title: Fish is now a weapon?
Desc: kill someone with a fish
Set attack damage 

Dead dead bushes: black dead bush.

Fish friend

By silly Silverwing

You can tame it like a dog.

It will fight with you if you are in the same water.

You can pick it up by right click.

And put it in water or ground by placing it.

It won't take damage from being out of the water if it has a owner.

Fish will swim around you.

when you become the owner of a fish it gets a heart effects:

Fish will swim like other fish, but then as an item.

natural spawn like cod?

TODO THINK
ga ik fish entity extended van tamaeble of fishgroup?..
nadenken wat het beste kan doen en wat ik eigenlijk in mijn mod wil hebben.
ik kan fish functies overnemen en in tamable extended fish zetten
ik hoe niet in groups te zwemmen

REAL TODO

Fish Entity spawn:

fish Entity workable..:

fish entity add wolf functions?:
or
fish entity add fish functions?:

fish entity out of water damage when no owner:

fish entity render model:

rotate fish in image, not in json!!: done fish2

on dead it drops a minecraft raw cod: test

pick up fish entity:

only when you are the owner you can pick it up when it is a living entity and not a item:

when you drop it, it becomes a living entity:
look at pick up mob mods

spawn fish entity on block click with fish item:
look at fish bucket or spawn egg for this

adjust attack damage of item:

adjust attack speed of item:

naturale spawning:

add own advencement root for all the achievements

You can use your fish as weapon when you hold it.

You can eat it and you will even get a advancement for it.

Extra
fish slap sound when you hit an entity.

when 1.17 gets out for forge mdk, then i could use Axolotl entity from it and remove our custom TameableFishEntity class.
or just not use it and keep it there.. unless Axolotl extends TameableEntity then i will keep using this one.


getFish
returns een fish item met de gegevens van een fish entity zoals in fish bucket

item spawns een fish met de gegevens van de entity, again, kijk naar fish bucket

setFish
zorgt ervoor dat de fish item een copy krijgt van fish entity zoals fish bucket

fish item en entity moeten de zelfde tag name krijgen als 1 van de 2 een name gekregen heeft:
item naar entity: test
entity naar item: test
 * 
 */

/*
 * ADVENCEMENTS
 * 
 * Advancement
 * 
 * Title: how could you?!
Description: Eat a fish.

Title: Fish is now a weapon?
Desc: kill someone with a fish
 * 
Title friendly fire!
Description kill a fish with a fish. 
minecraft:player_killed_entity


Title you maded a (fish) friend
Description tame a fish
Pick up a fish.
minecraft:tame_animal


Root: fish friend(s)
Icon fish 

Title lets go for a walk
Description leash a fish 

Title mmm, fish sticks!
Description cook a fish
Not possible..
Or
minecraft:consume_item


Two branches
Good advancements fish with halo 

Bad advancements fish with devil horns 

Extra
Als player dood gaat then the fish item turns into a fish entity and teleports to the player.



minecraft:thrown_item_picked_up_by_entity 

Extra
Add fish for 1 emerald to fish village
Title selling out a friend
Description sell your fish buddy to a fisher village.
minecraft:villager_trade
 */

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FishFriend.MOD_ID)
public class FishFriend {
	// Directly reference a log4j logger.
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "fishfriend";

	public FishFriend() {
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

		ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		EntityTypeInit.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void setupClient(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.FISH.get(), FishEntityRender::new);
	}

	// 1.17 render
	/*
	 * public void e (EntityRenderersEvent.RegisterRenderers event) {
	 * event.registerEntityRenderingHandler(EntityTypeInit.FISH.get(),
	 * FishEntityRender::new); }
	 */

	private void setup(final FMLCommonSetupEvent event) {
		// https://github.com/TheGreyGhost/MinecraftByExample/blob/master/src/main/java/minecraftbyexample/mbe11_item_variants/StartupClientOnly.java
		/*
		 * we need to attach the fullness PropertyOverride to the Item, but there are
		 * two things to be careful of: 1) We should do this on a client installation
		 * only, not on a DedicatedServer installation. Hence we need to use
		 * FMLClientSetupEvent. 2) FMLClientSetupEvent is multithreaded but
		 * ItemModelsProperties is not multithread-safe. So we need to use the
		 * enqueueWork method, which lets us register a function for synchronous
		 * execution in the main thread after the parallel processing is completed
		 */
		event.enqueueWork(() -> {
			GlobalEntityTypeAttributes.put(EntityTypeInit.FISH.get(), FishEntity.setCustomAttributes().create());
		});
	}

	// 1.17 change attributes like attack for entities
	/*
	 * public void registerAttributes(EntityAttributeCreationEvent event) {
	 * event.put(EntityType.PIG,
	 * PigEntity.func_234215_eI_().createMutableAttribute(Attributes.ATTACK_DAMAGE,
	 * 0.5D).create()); }
	 */

	@SubscribeEvent
	public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
		ModSpawnEggItem.initSpawnEggs();
	}

}
