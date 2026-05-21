package com.azulc.morestatues.block.entity.client;


import com.azulc.morestatues.morestatues;
import com.azulc.morestatues.block.base.variantRegistry;
import com.azulc.morestatues.block.entity.MoreStatueEntityBlock;
import com.azulc.morestatues.block.statue.RenderStyle;
import com.azulc.morestatues.block.statue.Tallblock;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
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
        BlockState state = animatable.getBlockState();
        String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
        int variantIndex = state.hasProperty(Tallblock.VARIANT) ? state.getValue(Tallblock.VARIANT) : 0;

        if (variantIndex > 0) {
            var blockMap = variantRegistry.TEX_REGISTRY.get(id);
            if (blockMap != null) {
                variantRegistry.TexVariantData data = blockMap.get(variantIndex);

                // If explicit texture modification mapping rule exists:
                if (data != null) {
                    // Yields paths like: "textures/block/zombie_statue_husk.png" or "textures/block/zombie_statue_2.png"
                    return ResourceLocation.fromNamespaceAndPath("morestatues", "textures/block/" + id + "_" + data.textureSuffix() + ".png");
                }
            }
        }

        // Default asset fallback variant index 0
        return ResourceLocation.fromNamespaceAndPath("morestatues", "textures/block/" + id + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MoreStatueEntityBlock animatable) {
        String id = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        return ResourceLocation.fromNamespaceAndPath(morestatues.MODID, "animations/block/" + id + ".animation.json");
    }
}