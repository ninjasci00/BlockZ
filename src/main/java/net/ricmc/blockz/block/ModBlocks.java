package net.ricmc.blockz.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ricmc.blockz.BlockzMod;
import net.ricmc.blockz.block.custom.BazeBlock;
import net.ricmc.blockz.block.custom.ExchangeBlock;
import net.ricmc.blockz.item.ModItems;
import net.ricmc.blockz.item.custom.BazeBlockItem;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static net.ricmc.blockz.item.ModItems.ITEMS;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlockzMod.MOD_ID);

    // Generic blocks
    public static final DeferredBlock<Block> SMOOTHDIRTLIGHT_BLOCK = registerBlock("smoothdirtlight_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );

    public static final DeferredBlock<Block> SMOOTHDIRTDARK_BLOCK = registerBlock("smoothdirtdark_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );

    public static final DeferredBlock<Block> EXCHANGE_BLOCK = registerBlock("exchange_block",
            () -> new ExchangeBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .requiresCorrectToolForDrops()
            )
    );

    // Custom block using BazeBlockItem
    public static final DeferredBlock<BazeBlock> BAZE_BLOCK = registerBlock(
            "baze_block",
            () -> new BazeBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()),
            (block, props) -> new BazeBlockItem(block, props)
    );

    // Generic registration method for normal blocks
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    // Unified registration for blocks with custom items
    private static <T extends Block> DeferredBlock<T> registerBlock(
            String name,
            Supplier<T> block,
            BiFunction<T, Item.Properties, BlockItem> itemCreator
    ) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> itemCreator.apply(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
