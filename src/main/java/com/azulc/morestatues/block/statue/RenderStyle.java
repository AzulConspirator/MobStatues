package com.azulc.morestatues.block.statue;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public enum RenderStyle {
    SOLID {
        @Override
        public RenderType getEntityRenderType(ResourceLocation texture) {
            return RenderType.entityCutoutNoCull(texture);
        }
    },
    TRANSLUCENT {
        @Override
        public RenderType getEntityRenderType(ResourceLocation texture) {
            return RenderType.entityTranslucent(texture);
        }
    },
    COMPOSITE_ICE {
        @Override
        public RenderType getEntityRenderType(ResourceLocation texture) {
            return RenderType.entityCutoutNoCull(texture);
        }
    };
    public abstract RenderType getEntityRenderType(ResourceLocation texture);
}