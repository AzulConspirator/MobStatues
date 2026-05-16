package com.azulc.morestatues.block.statue;

import net.minecraft.world.level.block.Block;

public enum StatueVariant {
    TALL(Tallblock::new),
    LONG(Longblock::new),
    WALL(Wallblock::new);

    private final java.util.function.Function<net.minecraft.world.level.block.state.BlockBehaviour.Properties, Block> factory;

    StatueVariant(java.util.function.Function<net.minecraft.world.level.block.state.BlockBehaviour.Properties, Block> factory) 
    {
        this.factory = factory;
    }

    public Block create(net.minecraft.world.level.block.state.BlockBehaviour.Properties props) 
    {
        return this.factory.apply(props);
    }
}