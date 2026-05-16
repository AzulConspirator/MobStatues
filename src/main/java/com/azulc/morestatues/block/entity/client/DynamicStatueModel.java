package com.azulc.morestatues.block.entity.client;


import com.azulc.morestatues.morestatues;
import com.azulc.morestatues.block.entity.MoreStatueEntityBlock;
import com.azulc.morestatues.block.statue.RenderStyle;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class DynamicStatueModel extends DefaultedBlockGeoModel<MoreStatueEntityBlock> {

    public DynamicStatueModel() {
        // Point the asset base namespace to your mod
        super(ResourceLocation.fromNamespaceAndPath(morestatues.MODID, "dummy"));
    }



        @Override
	public RenderType getRenderType(MoreStatueEntityBlock animatable, ResourceLocation texture) {
        String id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        RenderStyle style = morestatues.STATUE_STYLES.getOrDefault(id, RenderStyle.SOLID);
        //handcrafted_morestatues.LOGGER.info("["+ handcrafted_morestatues.MODID+"] Model: Using render type " + style + " for " + id);
        if (style == RenderStyle.TRANSLUCENT) 
        {
            return RenderType.entityTranslucent(texture);
        }
        else
        {
            return RenderType.entityCutoutNoCull(texture, false);
        }
	}

    @Override
    public ResourceLocation getModelResource(MoreStatueEntityBlock animatable) {
        String id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        return ResourceLocation.fromNamespaceAndPath(morestatues.MODID, "geo/block/" + id + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MoreStatueEntityBlock animatable) {
        String id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        return ResourceLocation.fromNamespaceAndPath(morestatues.MODID, "textures/block/" + id + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MoreStatueEntityBlock animatable) {
        String id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        return ResourceLocation.fromNamespaceAndPath(morestatues.MODID, "animations/block/" + id + ".animation.json");
    }
}