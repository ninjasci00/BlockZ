package net.ricmc.blockz.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.ricmc.blockz.block.ModBlocks;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Standard blocks
        dropSelf(ModBlocks.SMOOTHDIRTDARK_BLOCK.get());
        dropSelf(ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get());
        dropSelf(ModBlocks.EXCHANGE_BLOCK.get());
        dropSelf(ModBlocks.SMOOTHCOBBLESTONEDARK_BLOCK.get());
        dropSelf(ModBlocks.SMOOTHCOBBLESTONELIGHT_BLOCK.get());

        // CopperBlockz variants — each drops itself
        dropSelf(ModBlocks.COPPER_BLOCKZ_0.get());
        dropSelf(ModBlocks.COPPER_BLOCKZ_1.get());
        dropSelf(ModBlocks.COPPER_BLOCKZ_2.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }

    // Optional: Helper if you wanted a single custom LootTable for special behavior
    private LootTable.Builder createSimpleDrop(Block block) {
        return LootTable.lootTable().withPool(
                applyExplosionCondition(block,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(block))
                )
        );
    }
}
