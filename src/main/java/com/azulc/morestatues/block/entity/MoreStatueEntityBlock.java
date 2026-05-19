package com.azulc.morestatues.block.entity;

import com.azulc.morestatues.morestatues;
import com.azulc.morestatues.block.base.baseblock;
import com.azulc.morestatues.block.base.variantRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.RenderUtil;

public class MoreStatueEntityBlock extends BlockEntity implements GeoBlockEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    
    public MoreStatueEntityBlock(BlockPos pos, BlockState state) {
        super(morestatues.STATUE_ENTITY.get(), pos, state);
    }
/*     @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<MoreStatueEntityBlock>(this, "controller", 0, this::predicate));
    } */

        @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            BlockState state = event.getAnimatable().getBlockState();
            String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
            int variantIndex = state.hasProperty(baseblock.VARIANT) ? state.getValue(baseblock.VARIANT) : 0;

            String activeAnimation = "idle"; // Fallback layout standard

            if (variantIndex > 0) {
                var blockMap = variantRegistry.REGISTRY.get(id);
                if (blockMap != null) {
                    variantRegistry.VariantData data = blockMap.get(variantIndex);
                    if (data != null && data.hasCustomPose()) {
                        activeAnimation = data.poseId(); // Switch directly to your declared tracking string
                    }
                }
            }

            return event.setAndContinue(RawAnimation.begin().thenLoop(activeAnimation));
        }));
        }
    
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }
    
}
