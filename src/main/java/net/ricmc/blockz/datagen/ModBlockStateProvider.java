package net.ricmc.blockz.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.ricmc.blockz.BlockzMod;
import net.ricmc.blockz.block.ModBlocks;
import net.ricmc.blockz.block.custom.CopperBlockz;

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

        // Automatically handle all copper variants
        registerCopperVariants();
    }

    private void registerCopperVariants() {
        DeferredBlock<?>[] copperVariants = new DeferredBlock[]{
                ModBlocks.COPPER_BLOCKZ_0,
                ModBlocks.COPPER_BLOCKZ_1,
                ModBlocks.COPPER_BLOCKZ_2
        };

        for (int i = 0; i < copperVariants.length; i++) {
            final int variant = i;
            DeferredBlock<?> block = copperVariants[i];

            // Blockstate variants
            getVariantBuilder(block.get()).forAllStates(state ->
                    ConfiguredModel.builder()
                            .modelFile(models().cubeAll("copper_blockz_" + variant,
                                    modLoc("block/copper_blockz_" + variant)))
                            .build()
            );

            // Item model points to the correct block model
            itemModels().withExistingParent("copper_blockz_" + variant,
                    modLoc("block/copper_blockz_" + variant));
        }

        // Optional: set the main "copper_blockz" item to variant 0 for inventory
        itemModels().withExistingParent("copper_blockz", modLoc("block/copper_blockz_0"));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
