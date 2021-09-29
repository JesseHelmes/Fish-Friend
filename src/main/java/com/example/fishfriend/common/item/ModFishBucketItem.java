package com.example.fishfriend.common.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * Exists to work around a limitation with Spawn Eggs: Spawn Eggs require an
 * EntityType, but EntityTypes are created AFTER Items. Therefore it is
 * "impossible" for modded spawn eggs to exist. This class gets around it by
 * passing "null" to the SpawnEggItem constructor and doing the initialisation
 * after registry events have finished firing.
 */
/*public class ModFishBucketItem extends FishBucketItem {
	protected static final List<FishBucketItem> UNADDED_FISH_BUCKETS = new ArrayList<FishBucketItem>();
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	/*
	 * public ModFishBucketItem(Supplier<? extends EntityType<?>> fishTypeIn,
	 * Supplier<? extends Fluid> fluid, Properties builder) { super(fishTypeIn,
	 * fluid, builder); // TODO Auto-generated constructor stub }
	 * 
	 * public ModFishBucketItem(EntityType<?> fishTypeIn, Fluid fluid, Properties
	 * builder) { super(fishTypeIn, fluid, builder); // TODO Auto-generated
	 * constructor stub }
	 */

	/*public ModFishBucketItem(final NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, Fluid fluid,
			Properties builder) {
		super(null, fluid, builder);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_FISH_BUCKETS.add(this);
	}

	public ModFishBucketItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, Fluid fluid,
			Properties builder) {
		super(null, fluid, builder);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_FISH_BUCKETS.add(this);
	}

	public static void initFishBuckets() {
		final Map<EntityType<?>, FishBucketItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class,
				null, "field_195987_b");
		DefaultDispenseItemBehavior dispenseBehaviour = new DefaultDispenseItemBehavior() {
			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				type.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction),
						SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		};

		for (final FishBucketItem fishBucket : UNADDED_FISH_BUCKETS) {
			EGGS.put(fishBucket.getFishType(), fishBucket);
			DispenserBlock.registerDispenseBehavior(fishBucket, dispenseBehaviour);
		}
		UNADDED_FISH_BUCKETS.clear();
	}

	@Override
	protected EntityType<?> getFishType() {
		return this.entityTypeSupplier.get();
	}
}*/
