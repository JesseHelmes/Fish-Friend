package com.example.fishfriend.entity.backup;

import java.util.UUID;

import javax.annotation.Nullable;

import com.example.fishfriend.core.init.EntityTypeInit;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BegGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


//there is a lot that i have to work out :T
public class FishEntity extends TameableFishEntity {

	private UUID angerTarget;

	public FishEntity(EntityType<? extends FishEntity> type, World worldIn) {
		super(type, worldIn);
		this.setTamed(false);
	}

	protected void registerGoals() {
		//this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		// this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F,
		// false));//custom water goal
		this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
		// this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));//custom
		// water goal
		// this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));//custom water
		// goal
		// this.targetSelector.addGoal(3, (new
		// HurtByTargetGoal(this)).setCallsForHelp());
	}

	public static AttributeModifierMap.MutableAttribute func_234176_m_() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 0.3F)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume() {
		return 0.4F;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required.
	 * For example, zombies and skeletons use this to react to sunlight and start to
	 * burn.
	 */
	public void livingTick() {
		super.livingTick();

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		super.tick();

	}

	public void setTamed(boolean tamed) {
		super.setTamed(tamed);
		if (tamed) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
			this.setHealth(20.0F);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
		}

		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}

	public ActionResultType interact(PlayerEntity playerIn, Hand handIn) {
		return ActionResultType.PASS;
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it
	 * (wheat, carrots or seeds depending on the animal type)
	 */
	public boolean isBreedingItem(ItemStack stack) {
		Item item = stack.getItem();
		return false;
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	public int getMaxSpawnedInChunk() {
		return 8;
	}

	@Nullable
	public UUID getAngerTarget() {
		return this.angerTarget;
	}

	public void setAngerTarget(@Nullable UUID target) {
		this.angerTarget = target;
	}

	/*
	 * public FishEntity getChild(ServerWorld serverWorldIn, AgeableEntity enity) {
	 * FishEntity fishEntity = EntityTypeInit.FISH.create(serverWorldIn); UUID uuid
	 * = this.getOwnerId(); if (uuid != null) { fishEntity.setOwnerId(uuid);
	 * fishEntity.setTamed(true); }
	 * 
	 * return fishEntity; }
	 */

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	/*
	 * public boolean canMateWith(AnimalEntity otherAnimal) { if (otherAnimal ==
	 * this) { return false; } else if (!this.isTamed()) { return false; } else if
	 * (!(otherAnimal instanceof WolfEntity)) { return false; } else { WolfEntity
	 * wolfentity = (WolfEntity)otherAnimal; if (!wolfentity.isTamed()) { return
	 * false; } else if (wolfentity.isEntitySleeping()) { return false; } else {
	 * return this.isInLove() && wolfentity.isInLove(); } } }
	 */

	public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
		if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
			if (target instanceof FishEntity) {
				FishEntity fishEntity = (FishEntity) target;
				return !fishEntity.isTamed() || fishEntity.getOwner() != owner;
			} else if (target instanceof PlayerEntity && owner instanceof PlayerEntity
					&& !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
				return false;
			} else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity) target).isTame()) {
				return false;
			} else {
				return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
			}
		} else {
			return false;
		}
	}

	/*
	 * public boolean canBeLeashedTo(PlayerEntity player) { return
	 * !this.func_233678_J__() && super.canBeLeashedTo(player); }
	 */

}
