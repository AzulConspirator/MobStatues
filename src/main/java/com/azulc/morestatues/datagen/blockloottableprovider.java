package com.azulc.morestatues.datagen;

import java.util.Set;

import com.azulc.morestatues.morestatues;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class blockloottableprovider extends BlockLootSubProvider 
{
    protected blockloottableprovider(HolderLookup.Provider Registry) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(),Registry);
    }

    @Override
    protected void generate() 
    {
        for (Block block : this.getKnownBlocks())
        {
            dropSelf(block);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return morestatues.BLOCKS.getEntries().stream().map(Holder::value).toList();
    }
}
