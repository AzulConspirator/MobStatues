package com.azulc.morestatues.block.base;

import java.util.HashMap;
import java.util.Map;

public class variantRegistry {
    public static final Map<String, Map<Integer, TexVariantData>> TEX_REGISTRY = new HashMap<>();
    public static final Map<String, Map<Integer, PoseVariantData>> POSE_REGISTRY = new HashMap<>();
    public record TexVariantData(
        String textureSuffix
    ) {}
    public record PoseVariantData(
        String PoseSuffix
    ) {}

    /**
     * @param id             The block ID string
     * @param variantNum     The integer index
     * @param hasCustomPose  has unique animation
     * @param poseId         The animation name
     * @param hasCustomTex    has unique textures?
     * @param textureSuffix  The custom file name affix (e.g., "husk" -> zombie_statue_husk.png)
     */
    public static void TextureRegister(String id, int variantNum, String textureSuffix) {
        TEX_REGISTRY.computeIfAbsent(id, k -> new HashMap<>()).put(variantNum, new TexVariantData(textureSuffix));
    }
    public static void PoseRegister(String id, int variantNum, String PoseSuffix) {
        POSE_REGISTRY.computeIfAbsent(id, k -> new HashMap<>()).put(variantNum, new PoseVariantData(PoseSuffix));
    }

    // Helper helper to dynamically determine the total max variant integer assigned to a block ID
    public static int getMaxTexVariant(String id) {
        var map = TEX_REGISTRY.get(id);
        return map == null ? 0 : map.keySet().stream().max(Integer::compareTo).orElse(0);
    }
    
    // Helper helper to dynamically determine the total max variant integer assigned to a block ID
    public static int getMaxPoseVariant(String id) {
        var map = POSE_REGISTRY.get(id);
        return map == null ? 0 : map.keySet().stream().max(Integer::compareTo).orElse(0);
    }
}