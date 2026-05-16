package com.azulc.handcrafted_morestatues.block.entity.client;


import com.azulc.handcrafted_morestatues.handcrafted_morestatues;
import com.azulc.handcrafted_morestatues.block.entity.MoreStatueEntityBlock;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class DynamicStatueModel extends DefaultedBlockGeoModel<MoreStatueEntityBlock> {

    public DynamicStatueModel() {
        // Point the asset base namespace to your mod
        super(ResourceLocation.fromNamespaceAndPath(handcrafted_morestatues.MODID, "dummy"));
    }

    // GeckoLib4 uses these methods to determine paths dynamically based on the block state in the world
    @Override
    public ResourceLocation getModelResource(MoreStatueEntityBlock animatable) {
        String id = animatable.getBlockState().getBlock().getLootTable().location().getPath();
        return ResourceLocation.fromNamespaceAndPath(handcrafted_morestatues.MODID, "geo/" + id + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MoreStatueEntityBlock animatable) {
        String id = animatable.getBlockState().getBlock().getLootTable().location().getPath();
        return ResourceLocation.fromNamespaceAndPath(handcrafted_morestatues.MODID, "textures/" + id + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MoreStatueEntityBlock animatable) {
        String id = animatable.getBlockState().getBlock().getLootTable().location().getPath();
        return ResourceLocation.fromNamespaceAndPath(handcrafted_morestatues.MODID, "animations/" + id + ".animation.json");
    }
}