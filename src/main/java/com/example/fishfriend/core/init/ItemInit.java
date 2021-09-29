package com.example.fishfriend.core.init;

import com.example.fishfriend.FishFriend;
import com.example.fishfriend.common.item.FishItem;
//import com.example.fishfriend.common.item.ModFishBucketItem;
import com.example.fishfriend.common.item.ModSpawnEggItem;

import net.minecraft.fluid.Fluids;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			FishFriend.MOD_ID);

	// fish itself comes here
//	/.food(Foods.COD)
	public static final RegistryObject<FishItem> FISH = ITEMS.register("fish",
	() -> new FishItem(ItemTier.DIAMOND, 100.5F, -3.0F,// damage 2.0F
			(new Item.Properties()).group(ItemGroup.MISC).food(Foods.COD)));//.maxStackSize(1)

	//https://github.com/TeamMetallurgy/Aquaculture/blob/master/src/main/java/com/teammetallurgy/aquaculture/init/AquaItems.java
//	public static final RegistryObject<FishBucketItem> FISH_BUCKET = ITEMS.register("fish_bucket",
//			() -> new FishBucketItem(EntityTypeInit.FISH.get(), Fluids.WATER,
//					(new Item.Properties()).maxStackSize(1).group(ItemGroup.MISC)));

	//to get color code: https://www.mathsisfun.com/hexadecimal-decimal-colors.html
	//6987410, 5932668
	//0x6A9E92, 0x5A867C
	//() -> EntityTypeInit.FISH.get()
	public static final RegistryObject<ModSpawnEggItem> FISH_SPAWN_EGG = ITEMS.register("fish_spawn_egg",
			() -> new ModSpawnEggItem(EntityTypeInit.FISH, 0xFF329F, 0x16777119,
					(new Item.Properties().group(ItemGroup.MISC))));

}
