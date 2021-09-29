package com.example.fishfriend.client.entity.model;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FishEntityModel<T extends Entity> extends SegmentedModel<T> {
	private final ModelRenderer body;

	public FishEntityModel() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		int i = 22;
		this.body = new ModelRenderer(this, 0, 0);
		this.body.addBox(0.0F, 0.0F, 0.0F, 12.0F, 16.0F, 7.0F);//is his the hit box? around the entity
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {

	}

	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of();
	}

	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body);
	}

	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of();
	}

}
