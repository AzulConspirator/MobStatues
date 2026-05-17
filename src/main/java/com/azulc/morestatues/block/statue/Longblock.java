package com.azulc.morestatues.block.statue;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.azulc.morestatues.morestatues;
import com.azulc.morestatues.block.base.baseblock;
import com.azulc.morestatues.block.entity.MoreStatueEntityBlock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Longblock extends baseblock{
    public static final MapCodec<Longblock> CODEC = simpleCodec(Longblock::new);
    public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 5, 16);

    public Longblock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
            .setValue(PART, BedPart.FOOT)
            .setValue(FACING, Direction.NORTH)
            .setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
        VoxelShape registeredShape = morestatues.STATUE_SHAPES.getOrDefault(id, SHAPE);
        // note: it applies to both halves (less headache for directionality), best to keep them connected on the sides
        // default is practically a slab, so it should be fine for most statues
        return registeredShape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, PART);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(PART) == BedPart.FOOT) {
            return Block.canSupportRigidBlock(level, pos.below());
        } else {
            BlockState neighbor = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
            return neighbor.is(this) && neighbor.getValue(PART) == BedPart.FOOT;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection();
        BlockPos pos = context.getClickedPos();
        BlockPos headPos = pos.relative(facing);

        Level level = context.getLevel();
        if (level.getBlockState(headPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(headPos)) {
            return this.defaultBlockState().setValue(FACING, facing).setValue(PART, BedPart.FOOT);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            BlockPos headPos = pos.relative(state.getValue(FACING));
            level.setBlock(headPos, state.setValue(PART, BedPart.HEAD), 3);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        // If the other half is broken, break this half too
        Direction connectionDir = getDirectionToOtherHalf(state.getValue(PART), state.getValue(FACING));
        if (direction == connectionDir) {
            return neighborState.is(this) && neighborState.getValue(PART) != state.getValue(PART) 
                   ? state 
                   : Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private static Direction getDirectionToOtherHalf(BedPart part, Direction facing) {
        return part == BedPart.FOOT ? facing : facing.getOpposite();
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK; // Prevent weird multi-block splitting with pistons
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == BedPart.FOOT ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.INVISIBLE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MoreStatueEntityBlock(pos, state);
    }

}