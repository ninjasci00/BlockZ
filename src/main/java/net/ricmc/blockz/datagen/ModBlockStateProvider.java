package net.ricmc.blockz.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.ricmc.blockz.BlockzMod;
import net.ricmc.blockz.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BlockzMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Register normal blocks
        blockWithItem(ModBlocks.EXCHANGE_BLOCK);
        blockWithItem(ModBlocks.SMOOTHDIRTDARK_BLOCK);
        blockWithItem(ModBlocks.SMOOTHDIRTLIGHT_BLOCK);
        blockWithItem(ModBlocks.SMOOTHCOBBLESTONEDARK_BLOCK);
        blockWithItem(ModBlocks.SMOOTHCOBBLESTONELIGHT_BLOCK);

        registerBlockVariants("copper_blockz", new DeferredBlock[]{
                ModBlocks.COPPER_BLOCKZ_0,
                ModBlocks.COPPER_BLOCKZ_1,
                ModBlocks.COPPER_BLOCKZ_2
        });

    }

    private void registerBlockVariants(String baseName, DeferredBlock<?>[] variants) {
        for (int i = 0; i < variants.length; i++) {
            final int variant = i;
            DeferredBlock<?> block = variants[i];
            // Register blockstate variants
            getVariantBuilder(block.get()).forAllStates(state ->
                    ConfiguredModel.builder()
                            .modelFile(models().cubeAll(baseName + "_" + variant,
                                    modLoc("block/" + baseName + "_" + variant)))
                            .build()
            );
            // Register item model pointing to correct block model
            itemModels().withExistingParent(baseName + "_" + variant,
                    modLoc("block/" + baseName + "_" + variant));
        }
        // Optional: set the main item to variant 0 for inventory
        itemModels().withExistingParent(baseName, modLoc("block/" + baseName + "_0"));
    }
    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
