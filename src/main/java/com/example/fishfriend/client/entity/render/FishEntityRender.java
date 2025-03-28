package com.example.fishfriend.client.entity.render;

import com.example.fishfriend.FishFriend;
import com.example.fishfriend.client.entity.model.FishEntityModel;
import com.example.fishfriend.entity.passive.FishEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FishEntityRender<T extends Entity> extends MobRenderer<FishEntity, FishEntityModel<FishEntity>> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(FishFriend.MOD_ID,
			"textures/entity/fish.png");

	public FishEntityRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new FishEntityModel<FishEntity>(), 0.3F);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getEntityTexture(FishEntity entity) {
		return TEXTURE;
	}

	/*protected void applyRotations(FishEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f));
		if (!entityLiving.isInWater()) {
			matrixStackIn.translate((double) 0.1F, (double) 0.1F, (double) -0.1F);
			matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F));
		}

	}*/
}
