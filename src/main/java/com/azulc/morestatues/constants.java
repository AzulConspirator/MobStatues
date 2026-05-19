package com.azulc.morestatues;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.Block;

public class constants {
    public final VoxelShape GhastShape()
	{
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Block.box(0, 32, 16, 16, 48, 24), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 16, 16, 16, 32, 24), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 0, 16, 16, 16, 24), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 32, -8, 16, 48, 0), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(16, 32, 0, 24, 48, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(-8, 32, 0, 0, 48, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 46, 0, 16, 48, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(16, 16, 0, 24, 32, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(-8, 16, 0, 0, 32, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(-8, 0, 0, 0, 16, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(16, 0, 0, 24, 16, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 16, -8, 16, 32, 0), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0, 0, -8, 16, 16, 0), BooleanOp.OR);
		return shape;
	}

	public VoxelShape RavagerShape()
	{
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Block.box(0, 0, 0, 16, 16, 16), BooleanOp.OR);
		return shape;
	}
}
