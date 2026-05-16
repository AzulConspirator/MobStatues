package com.azulc.handcrafted_morestatues.block.entity.client.renderer;

import com.azulc.handcrafted_morestatues.block.entity.MoreStatueEntityBlock;
import com.azulc.handcrafted_morestatues.block.entity.client.*;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DynamicStatueRenderer extends GeoBlockRenderer<MoreStatueEntityBlock> {
    public DynamicStatueRenderer() 
    {
        super(new DynamicStatueModel());
        this.addRenderLayer(new com.azulc.handcrafted_morestatues.block.entity.client.renderer.layer.IceOverlayLayer(this));
    }
    @Override
    public void render(MoreStatueEntityBlock animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
     {
        BlockState state = animatable.getBlockState();
        boolean isLongblock = state.hasProperty(net.minecraft.world.level.block.BedBlock.PART);
        boolean isTallblock = state.hasProperty(net.minecraft.world.level.block.state.properties.BlockStateProperties.DOUBLE_BLOCK_HALF);
        if (isLongblock)
        {
            if (state.getValue(net.minecraft.world.level.block.BedBlock.PART) != BedPart.FOOT) {
                return;
            }
        }
        if (isTallblock) 
        {
            if (state.getValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.DOUBLE_BLOCK_HALF) != DoubleBlockHalf.LOWER) {
                return;
            }
        }
        super.render(animatable, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
    }
}