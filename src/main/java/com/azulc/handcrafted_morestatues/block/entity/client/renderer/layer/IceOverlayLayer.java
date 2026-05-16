package com.azulc.handcrafted_morestatues.block.entity.client.renderer.layer;
import com.azulc.handcrafted_morestatues.block.entity.MoreStatueEntityBlock;
import com.azulc.handcrafted_morestatues.block.statue.RenderStyle;
import com.azulc.handcrafted_morestatues.handcrafted_morestatues;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class IceOverlayLayer extends GeoRenderLayer<MoreStatueEntityBlock> {

    public IceOverlayLayer(GeoRenderer<MoreStatueEntityBlock> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, MoreStatueEntityBlock animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        
        String id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        RenderStyle style = handcrafted_morestatues.STATUE_STYLES.getOrDefault(id, RenderStyle.SOLID);

        if (style == RenderStyle.COMPOSITE_ICE) {
            ResourceLocation iceTexture = ResourceLocation.fromNamespaceAndPath(
                handcrafted_morestatues.MODID, 
                "textures/blocks/" + id + "_overlay.png"
            );

            RenderType iceRenderType = RenderType.entityTranslucent(iceTexture);

            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, iceRenderType,bufferSource.getBuffer(iceRenderType), partialTick, packedLight, packedOverlay, 0xFFFFFFFF);
        }
    }
}