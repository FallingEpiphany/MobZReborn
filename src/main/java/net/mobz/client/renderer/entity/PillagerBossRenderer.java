package net.mobz.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.mobz.entity.PillagerBoss;

public class PillagerBossRenderer extends IllagerRenderer<PillagerBoss> {
    private static final ResourceLocation SKIN = new ResourceLocation("mobz:textures/entity/pillagerboss.png");

    public PillagerBossRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    protected void scale(PillagerBoss pilli, PoseStack matrixStack, float f) {
        matrixStack.scale(1.3F, 1.3F, 1.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(PillagerBoss entity) {
        return SKIN;
    }
}