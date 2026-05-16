package com.azulc.handcrafted_morestatues.block.entity.client;

import net.minecraft.resources.ResourceLocation;
import com.azulc.handcrafted_morestatues.handcrafted_morestatues;
import com.azulc.handcrafted_morestatues.block.entity.MoreStatueBlockItem;
import com.azulc.handcrafted_morestatues.block.statue.RenderStyle;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import software.bernie.geckolib.model.GeoModel;

public class DynamicStatueItemModel extends GeoModel<MoreStatueBlockItem> {

    @Override
	public RenderType getRenderType(MoreStatueBlockItem animatable, ResourceLocation texture) {
        String id = BuiltInRegistries.ITEM.getKey(animatable).getPath();
        RenderStyle style = handcrafted_morestatues.STATUE_STYLES.getOrDefault(id, RenderStyle.SOLID);
        //handcrafted_morestatues.LOGGER.info("["+ handcrafted_morestatues.MODID+"] ItemModel: Using render type " + style + " for " + id);
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