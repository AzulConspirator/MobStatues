package com.azulc.morestatues.datagen;

import java.util.concurrent.CompletableFuture;

import com.azulc.morestatues.morestatues;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class mobstatue_recipeprovider extends RecipeProvider implements IConditionBuilder{

    public mobstatue_recipeprovider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) 
    { 
        for (Block block : morestatues.BLOCKS.getEntries().stream().map(Holder::value).toList())
        {
            Item item = switch (BuiltInRegistries.BLOCK.getKey(block).getPath()) 
            {
                //case "creeper_statue" -> Items.CREEPER_HEAD;
                //case "skeleton_statue" -> Items.SKELETON_SKULL;
                //case "wither_skeleton_statue" -> Items.STONE_SWORD;
                //case "piglin_statue" -> Items.PIGLIN_HEAD;
                //case "stray_statue" -> Items.STRAY_SPAWN_EGG;
                case "zombie_statue" -> Items.ZOMBIE_HEAD;
                case "enderman_statue" -> Items.ENDER_PEARL;
                case "guardian_statue" -> Items.PRISMARINE_SHARD;
                case "bosswither_statue" -> Items.WITHER_SKELETON_SKULL;
                case "blaze_statue" -> Items.BLAZE_ROD;
                case "wolf_statue" -> Items.BEEF;
                case "ghast_statue" -> Items.GHAST_TEAR;
                case "ghast_fireball_statue" -> Items.FIRE_CHARGE;
                case "allay_statue" -> Items.COOKIE;
                case "vex_statue" -> Items.IRON_SWORD;
                default -> null; // Handle unexpected cases
            };
            if (item != null) {
                addShaped(block, item, recipeOutput);
            }
        }
    }
    private void addShaped(Block block_output,Item item, RecipeOutput recipeOutput) {
        
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block_output)
        .pattern("SSS")
        .pattern("SAS")
        .pattern("SSS")
        .define('S', Items.STICK)
        .define('A', item)
        .unlockedBy(getName(), has(Items.STICK))
        .save(recipeOutput, BuiltInRegistries.BLOCK.getKey(block_output).getPath());
    }
    
}
