package net.mobz.client.renderer.entity;

import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.mobz.entity.ArcherEntity;

public class ArcherRenderer extends HumanoidMobRenderer<ArcherEntity, PlayerModel<ArcherEntity>> {
    public ArcherRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(ArcherEntity archer) {
        return new ResourceLocation("mobz:textures/entity/archer.png");
    }
}