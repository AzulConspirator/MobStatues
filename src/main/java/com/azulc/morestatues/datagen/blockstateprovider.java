package com.azulc.morestatues.datagen;

import com.azulc.morestatues.morestatues;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class blockstateprovider extends BlockStateProvider
{
    public blockstateprovider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, morestatues.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() 
    {
        for (Block block : morestatues.BLOCKS.getEntries().stream().map(Holder::value).toList())
        {
            DirectionalBlock(block);
        }
    }

    private void DirectionalBlock(Block block)
    {
        
        getVariantBuilder(block)
            .forAllStates(state -> {
                Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                int yRot = switch (dir) 
                {
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0; // NORTH
                };
                return ConfiguredModel.builder().modelFile(models()
                .cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                    modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath())))
                .rotationY(yRot).build();
            });
    }
    
}
