package com.azulc.morestatues.block.entity.client.renderer;

import com.azulc.morestatues.block.entity.MoreStatueBlockItem;
import com.azulc.morestatues.block.entity.client.DynamicStatueItemModel;

import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DynamicStatueItemRenderer extends GeoItemRenderer<MoreStatueBlockItem> {
    public DynamicStatueItemRenderer() {
        super(new DynamicStatueItemModel());
        this.addRenderLayer(new com.azulc.morestatues.block.entity.client.renderer.layer.IceOverlayItemLayer(this));
    }
}