package com.example.fishfriend.common.item;

import com.example.fishfriend.core.init.ItemInit;
import com.example.fishfriend.entity.passive.FishEntity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

//look at fish bucket item to find out how you pick it up
public class FishItem extends TieredItem implements IVanishable {
	private final float attackDamage;
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	// private EntityType<FishEntity> fishEntity;
	private FishEntity fishEntity = null;

	public FishItem(IItemTier tierIn, float attackDamageIn, float attackSpeedIn, Item.Properties builderIn) {
		super(tierIn, builderIn);
		this.attackDamage = attackDamageIn + tierIn.getAttackDamage();
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Fish modifier",
				(double) this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Fish modifier",
				(double) attackSpeedIn, AttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	/**
	 * Current implementations of this method in child classes do not use the entry
	 * argument beside ev. They just raise the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damageItem(0, attacker, (entity) -> {
			entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
		});
		return true;
	}

	/**
	 * Called when a Block is destroyed using this Item. Return true to trigger the
	 * "Use Item" statistic.
	 */
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entityLiving) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
			stack.damageItem(0, entityLiving, (entity) -> {
				entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
			});
		}
		return true;
	}

	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit
	 * damage.
	 */
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
		return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers
				: super.getAttributeModifiers(equipmentSlot);
	}

	/**
	 * Called when this item is used when targetting a Block
	 */
	public ActionResultType onItemUse(ItemUseContext context) {
		return ActionResultType.PASS;
	}

	public void onLiquidPlaced(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
		if (worldIn instanceof ServerWorld) {
			this.placeFish((ServerWorld) worldIn, p_203792_2_, pos);
		}

	}

	private void placeFish(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
		Entity entity = this.getFish().spawn(worldIn, stack, (PlayerEntity) null, pos, SpawnReason.BUCKET, true, false);
		if (entity != null) {
			((AbstractFishEntity) entity).setFromBucket(true);
		}

	}

	public void setFish(FishEntity fishEntity) {
		this.fishEntity = fishEntity;
		// renames our fish with custom name from entity
		if (this.getDisplayName(this.getDefaultInstance()) != this.fishEntity.getCustomName()) {
			this.getDefaultInstance().setDisplayName(this.fishEntity.getCustomName());
		}
	}

	public EntityType<?> getFish() {
		// set custom name before returning fishEntity
		if (this.getDisplayName(this.getDefaultInstance()) != this
				.getDisplayName(ItemInit.FISH.get().getDefaultInstance())) {
			this.fishEntity.setCustomName(this.getDisplayName(this.getDefaultInstance()));
		}

		return this.fishEntity.getType();
	}

}
