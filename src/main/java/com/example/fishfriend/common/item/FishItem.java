package com.example.fishfriend.common.item;

import com.example.fishfriend.core.init.SoundsInit;
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
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

//look at fish bucket item to find out how you pick it up
public class FishItem extends TieredItem implements IVanishable {
	private final float attackDamage;
	/** Modifiers applied when the item is in the mainhand of a user. */
	private final Multimap<Attribute, AttributeModifier> attributeModifiers;
	// private FishEntity fishEntity = null;
	private EntityType<?> fishEntity = EntityType.COD;

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
		// https://minecraft.fandom.com/wiki/Player "sounds"
		// The pitch of a sound is how high or low the sound is.
		// plays sound on hitting entity with our fish item
		attacker.world.playSound((PlayerEntity) null, target.getPosX(), target.getPosY(), target.getPosZ(),
				SoundsInit.FISH_ITEM_SLAP.get(), attacker.getSoundCategory(), 1.75F, 0F);
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

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when
	 * this item is used on a Block, see {@link #onItemUse}.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
		if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
			/// this causes the user to use the item as food.
			return super.onItemRightClick(worldIn, playerIn, handIn);
		} else if (!(worldIn instanceof ServerWorld)) {
			return ActionResult.resultSuccess(itemstack);
		} else {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
			BlockPos blockpos = blockraytraceresult.getPos();
			if (worldIn.isBlockModifiable(playerIn, blockpos)
					&& playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {
				if (this.placeFish((ServerWorld) worldIn, itemstack, blockpos) == null) {
					return ActionResult.resultPass(itemstack);
				} else {
					if (!playerIn.abilities.isCreativeMode) {
						itemstack.shrink(1);
					}

					playerIn.addStat(Stats.ITEM_USED.get(this));
					return ActionResult.resultConsume(itemstack);
				}
			} else {
				return ActionResult.resultFail(itemstack);
			}
		}
	}

	private Entity placeFish(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
		Entity entity = this.getFishType().spawn(worldIn, stack, (PlayerEntity) null, pos, SpawnReason.SPAWN_EGG, true,
				false);
		if (entity != null) {
			// updates the name of the entity with the name of the item
			if (stack.hasDisplayName()) {
				entity.setCustomName(stack.getDisplayName());
			}
			((AbstractFishEntity) entity).setFromBucket(true);
		}

		return entity;
	}

	// FishEntity fishEntity
	public void setFishType(EntityType<?> fishEntity) {
		this.fishEntity = fishEntity;
	}

	public EntityType<?> getFishType() {
		return this.fishEntity;
	}

}
