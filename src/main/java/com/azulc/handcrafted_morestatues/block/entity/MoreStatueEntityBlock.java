package com.azulc.handcrafted_morestatues.block.entity;

import com.azulc.handcrafted_morestatues.handcrafted_morestatues;

import net.minecraft.core.BlockPos;
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
        super(handcrafted_morestatues.STATUE_ENTITY.get(), pos, state);
    }

    private PlayState predicate(AnimationState<MoreStatueEntityBlock> state) {
        state.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<MoreStatueEntityBlock>(this, "controller", 0, this::predicate));
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
