package com.azulc.morestatues.datagen;

import com.azulc.morestatues.morestatues;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class Itemmodelprovider  extends ItemModelProvider
{
    public Itemmodelprovider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, morestatues.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() 
    {
        for (Block block : morestatues.BLOCKS.getEntries().stream().map(Holder::value).toList())
        {
            String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
    }
}
