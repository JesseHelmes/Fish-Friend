package com.example.fishfriend.common.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
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
public class ModSpawnEggItem extends SpawnEggItem{

	protected static final List<ModSpawnEggItem> UNADDED_EGGS = new ArrayList<ModSpawnEggItem>();
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	public ModSpawnEggItem(final NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, final int primaryColor,
			final int secondaryColor, final Item.Properties properties) {
		super(null, primaryColor, secondaryColor, properties);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_EGGS.add(this);
	}

	public ModSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, final int primaryColor,
			final int secondaryColor, final Item.Properties properties) {
		super(null, primaryColor, secondaryColor, properties);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_EGGS.add(this);
	}

	public static void initSpawnEggs() {
		final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class,
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

		for (final SpawnEggItem spawnEgg : UNADDED_EGGS) {
			EGGS.put(spawnEgg.getType(null), spawnEgg);
			DispenserBlock.registerDispenseBehavior(spawnEgg, dispenseBehaviour);
		}
		UNADDED_EGGS.clear();
	}

	@Override
	public EntityType<?> getType(CompoundNBT nbt) {
		return this.entityTypeSupplier.get();
	}
}
