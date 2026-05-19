package com.azulc.morestatues.block.base;

import java.util.HashMap;
import java.util.Map;

public class variantRegistry {
    public static final Map<String, Map<Integer, VariantData>> REGISTRY = new HashMap<>();

    public record VariantData(
        boolean hasCustomPose, 
        String poseId, 
        boolean hasCustomTex, 
        String textureSuffix
    ) {}

    /**
     * @param id             The block ID string
     * @param variantNum     The integer index
     * @param hasCustomPose  has unique animation
     * @param poseId         The animation name
     * @param hasCustomTex    has unique textures?
     * @param textureSuffix  The custom file name affix (e.g., "husk" -> zombie_statue_husk.png)
     */
    public static void register(String id, int variantNum, boolean hasCustomPose, String poseId, boolean hasCustomTex, String textureSuffix) {
        REGISTRY.computeIfAbsent(id, k -> new HashMap<>())
                .put(variantNum, new VariantData(hasCustomPose, poseId, hasCustomTex, textureSuffix));
    }

    // Helper helper to dynamically determine the total max variant integer assigned to a block ID
    public static int getMaxVariant(String id) {
        var map = REGISTRY.get(id);
        return map == null ? 0 : map.keySet().stream().max(Integer::compareTo).orElse(0);
    }
}