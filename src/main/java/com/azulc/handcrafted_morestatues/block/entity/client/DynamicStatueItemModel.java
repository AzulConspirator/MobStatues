package com.azulc.handcrafted_morestatues.block.entity.client;

import net.minecraft.resources.ResourceLocation;
import com.azulc.handcrafted_morestatues.handcrafted_morestatues;
import com.azulc.handcrafted_morestatues.block.entity.MoreStatueBlockItem;

import net.minecraft.core.registries.BuiltInRegistries;
import software.bernie.geckolib.model.GeoModel;

public class DynamicStatueItemModel extends GeoModel<MoreStatueBlockItem> {

    @Override
    public ResourceLocation getModelResource(MoreStatueBlockItem animatable) {
        String id = BuiltInRegistries.ITEM.getKey(animatable).getPath();
        return ResourceLocation.fromNamespaceAndPath(handcrafted_morestatues.MODID, "geo/blocks/" + id + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MoreStatueBlockItem animatable) {
        String id = BuiltInRegistries.ITEM.getKey(animatable).getPath();
        return ResourceLocation.fromNamespaceAndPath(handcrafted_morestatues.MODID, "textures/blocks/" + id + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MoreStatueBlockItem animatable) {
        String id = BuiltInRegistries.ITEM.getKey(animatable).getPath();
        return ResourceLocation.fromNamespaceAndPath(handcrafted_morestatues.MODID, "animations/blocks/" + id + ".animation.json");
    }
}