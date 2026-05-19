package com.azulc.morestatues.block.base;

import java.util.List;

import com.azulc.morestatues.block.entity.MoreStatueEntityBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public abstract class baseblock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock,EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 10);
    public baseblock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
            .setValue(FACING, Direction.NORTH)
            .setValue(WATERLOGGED, false).setValue(VARIANT, 0));
            
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, VARIANT);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType().equals(Fluids.WATER)).setValue(VARIANT, 0);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(Items.STICK)) {
            if (!level.isClientSide) {
                String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
                int maxVariant = variantRegistry.getMaxVariant(blockId);

                if (maxVariant > 0) {
                    int currentVariant = state.getValue(VARIANT);
                    int nextVariant = (currentVariant + 1) > maxVariant ? 0 : currentVariant + 1;

                    // 1. Update the clicked block state directly
                    BlockState newState = state.setValue(VARIANT, nextVariant);
                    level.setBlockAndUpdate(pos, newState);

                    // 2. Structural Sync Strategy:
                    // Handle Tallblocks (Vertical Sibling Alignment)
                    if (state.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
                        var half = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);
                        BlockPos siblingPos = half == net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER ? pos.above() : pos.below();
                        BlockState siblingState = level.getBlockState(siblingPos);
                        if (siblingState.is(this)) {
                            level.setBlockAndUpdate(siblingPos, siblingState.setValue(VARIANT, nextVariant));
                        }
                    }
                    
                    if (state.hasProperty(BlockStateProperties.BED_PART)) {
                        Direction facing = state.getValue(FACING);
                        BlockPos siblingPos = state.getValue(BlockStateProperties.BED_PART) == BedPart.FOOT ? pos.relative(facing.getClockWise()) : pos.relative(facing.getCounterClockWise());
                        BlockState siblingState = level.getBlockState(siblingPos);
                        if (siblingState.is(this)) {
                            level.setBlockAndUpdate(siblingPos, siblingState.setValue(VARIANT, nextVariant));
                        }
                    }
                    
                    
                    return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext properties, List<Component> tooltip, TooltipFlag flag) 
    {
        String blockId = BuiltInRegistries.BLOCK.getKey(this).getPath();
        int maxVariant = variantRegistry.getMaxVariant(blockId);
        if (maxVariant > 0) {
            int totalVariantsCount = maxVariant + 1;
            tooltip.add(Component.translatable("tooltip.morestatues.has_variants", totalVariantsCount));
        }
        super.appendHoverText(stack, properties, tooltip, flag);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MoreStatueEntityBlock(pos, state);
    }
}