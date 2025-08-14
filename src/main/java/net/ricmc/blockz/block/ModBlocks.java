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

import java.util.function.Function;
import java.util.function.Supplier;

import static net.ricmc.blockz.item.ModItems.ITEMS;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlockzMod.MOD_ID);

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
    public static final DeferredBlock<BazeBlock> BAZE_BLOCK =
            registerBlock("baze_block",
                    () -> new BazeBlock(BlockBehaviour.Properties.of().strength(1f).requiresCorrectToolForDrops()),
                    (block) -> new BazeBlockItem((Block) block, new Item.Properties())
            );


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(
            String name,
            Supplier<T> block,
            Function<T, BlockItem> itemFactory
    ) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        ITEMS.register(name, () -> itemFactory.apply(toReturn.get()));
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
