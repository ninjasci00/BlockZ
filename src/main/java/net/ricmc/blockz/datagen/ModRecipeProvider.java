package net.ricmc.blockz.datagen;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.ricmc.blockz.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {


        //SMOOTHDIRT RECIPES
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(), 64)
                .pattern("XXX")
                .pattern("XOX")
                .pattern("XXX")
                .define('X', Items.DIRT)
                .define('O', Items.WATER_BUCKET)
                .unlockedBy("has_water_and_dirt_block", has(ModBlocks.SMOOTHDIRTLIGHT_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(), 1)
                .requires(ModBlocks.SMOOTHDIRTDARK_BLOCK)
                .unlockedBy("has_smoothdirtdark_block", has(ModBlocks.SMOOTHDIRTDARK_BLOCK))
                .save(recipeOutput, "blockzmod:exchange_smoothdirtdark_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SMOOTHDIRTDARK_BLOCK.get(), 1)
                .requires(ModBlocks.SMOOTHDIRTLIGHT_BLOCK)
                .unlockedBy("has_smoothdirtlight_block", has(ModBlocks.SMOOTHDIRTDARK_BLOCK))
                .save(recipeOutput, "blockzmod:exchange_smoothdirtlight_block");

        //SMOOTHDIRT STICK


    }
}
