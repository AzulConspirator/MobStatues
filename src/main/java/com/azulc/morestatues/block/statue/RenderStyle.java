package com.azulc.morestatues.block.statue;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public enum RenderStyle {
    SOLID {
        @Override
        public RenderType getEntityRenderType(ResourceLocation texture) {
            // Your working discovery: standard solid rendering bypassing the block atlas
            return RenderType.entityCutoutNoCull(texture);
        }
    },
    TRANSLUCENT {
        @Override
        public RenderType getEntityRenderType(ResourceLocation texture) {
            // The entity equivalent for smooth transparency/opacity blending
            return RenderType.entityTranslucent(texture);
        }
    },
    COMPOSITE_ICE {
        @Override
        public RenderType getEntityRenderType(ResourceLocation texture) {
            // The base pass renders the internal creature as a solid cutout object
            return RenderType.entityCutoutNoCull(texture);
        }
    };

    // Abstract bridge method to isolate Client-only RenderTypes from the Server registry
    public abstract RenderType getEntityRenderType(ResourceLocation texture);
}