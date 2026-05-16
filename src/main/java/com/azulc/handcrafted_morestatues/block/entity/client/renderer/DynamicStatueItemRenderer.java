package com.azulc.handcrafted_morestatues.block.entity.client.renderer;

import com.azulc.handcrafted_morestatues.block.entity.MoreStatueBlockItem;
import com.azulc.handcrafted_morestatues.block.entity.client.DynamicStatueItemModel;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DynamicStatueItemRenderer extends GeoItemRenderer<MoreStatueBlockItem> {
    public DynamicStatueItemRenderer() {
        super(new DynamicStatueItemModel());
        this.addRenderLayer(new com.azulc.handcrafted_morestatues.block.entity.client.renderer.layer.IceOverlayItemLayer(this));
    }
}