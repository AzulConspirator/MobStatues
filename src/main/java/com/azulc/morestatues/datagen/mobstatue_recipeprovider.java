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
            Item[] item = switch (BuiltInRegistries.BLOCK.getKey(block).getPath()) 
            {
                //case "creeper_statue" -> Items.CREEPER_HEAD;
                //case "skeleton_statue" -> Items.SKELETON_SKULL;
                //case "wither_skeleton_statue" -> Items.STONE_SWORD;
                //case "piglin_statue" -> Items.PIGLIN_HEAD;
                //case "stray_statue" -> Items.STRAY_SPAWN_EGG;
                case "zombie_statue" -> new Item[]{Items.ROTTEN_FLESH, Items.ZOMBIE_HEAD, Items.STONE};
                case "enderman_statue" -> new Item[]{Items.STICK, Items.ENDER_PEARL, Items.ENDER_EYE};
                case "guardian_statue" -> new Item[]{Items.ICE, Items.PRISMARINE_SHARD, Items.PRISMARINE_CRYSTALS};
                case "bosswither_statue" -> new Item[]{Items.STICK, Items.WITHER_SKELETON_SKULL,Items.WITHER_ROSE};
                case "blaze_statue" -> new Item[]{Items.BLAZE_ROD, Items.BLAZE_POWDER,Items.NETHER_BRICKS};
                case "breeze_statue" -> new Item[]{Items.BREEZE_ROD, Items.WIND_CHARGE,Items.TUFF_BRICKS};
                case "wolf_statue" -> new Item[]{Items.STONE, Items.BEEF, Items.BONE};
                case "ghast_statue" -> new Item[]{Items.STICK, Items.GHAST_TEAR, Items.GUNPOWDER};
                case "piglin_brute_statue" -> new Item[]{Items.BLACKSTONE, Items.PIGLIN_HEAD, Items.GOLD_INGOT};
                case "ghast_fireball_statue" -> new Item[]{Items.STICK, Items.FIRE_CHARGE,Items.STICK};
                case "allay_statue" -> new Item[]{Items.STICK, Items.COOKIE, Items.SWEET_BERRIES};
                case "vex_statue" -> new Item[]{Items.STICK, Items.IRON_SWORD, Items.IRON_INGOT};
                case "ravager_statue" -> new Item[]{Items.STICK, Items.SADDLE, Items.IRON_INGOT};
                case "slime_statue" -> new Item[]{Items.GLASS, Items.SLIME_BALL,Items.GLASS};
                case "magmacube_statue" -> new Item[]{Items.MAGMA_CREAM, Items.BASALT,Items.BLACKSTONE};
                default -> null; // Handle unexpected cases
            };
            if (item != null) {
                addShaped(block, item, recipeOutput);
            }
        }
    }
    private void addShaped(Block block_output,Item[] items, RecipeOutput recipeOutput) {
        
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block_output)
        .pattern("SSS")
        .pattern("SAS")
        .pattern("SBS")
        .define('S', items[0])
        .define('A', items[1])
        .define('B', items[2])
        .unlockedBy(getName(), has(Items.STICK))
        .save(recipeOutput, BuiltInRegistries.BLOCK.getKey(block_output).getPath());
    }
    
}
