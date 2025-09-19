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
import net.ricmc.blockz.block.custom.ExchangeBlock;
import net.ricmc.blockz.item.ModItems;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlockzMod.MOD_ID);

    // Normal blocks
    public static final DeferredBlock<Block> SMOOTHDIRTLIGHT_BLOCK = registerBlock("smoothdirtlight_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2f)
            )
    );
    public static final DeferredBlock<Block> SMOOTHDIRTDARK_BLOCK = registerBlock("smoothdirtdark_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2f)
            )
    );
    public static final DeferredBlock<Block> EXCHANGE_BLOCK = registerBlock("exchange_block",
            () -> new ExchangeBlock(BlockBehaviour.Properties.of()
                    .strength(1f)
                    .requiresCorrectToolForDrops()
            )
    );

    public static final DeferredBlock<Block> SMOOTHCOBBLESTONEDARK_BLOCK = registerBlock("smoothcobblestonedark_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(0.1f)
                    .speedFactor(1.2f)
            )
    );
    public static final DeferredBlock<Block> SMOOTHCOBBLESTONELIGHT_BLOCK = registerBlock("smoothcobblestonelight_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(0.1f)
                    .speedFactor(1.2f)
                    .requiresCorrectToolForDrops()
            )
    );

    // Copper variants handled directly
    public static final DeferredBlock<Block> COPPER_BLOCKZ_0 = registerBlock("copper_blockz_0",
            () -> new Block(BlockBehaviour
                    .Properties.of()

                    .sound(SoundType.COPPER)
                    .strength(0.3f, 6.0f)

                    .requiresCorrectToolForDrops()
            )
    );
    public static final DeferredBlock<Block> COPPER_BLOCKZ_1 = registerBlock("copper_blockz_1",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.COPPER)
                    .strength(0.3f, 6.0f)

                    .requiresCorrectToolForDrops()
            )
    );
    public static final DeferredBlock<Block> COPPER_BLOCKZ_2 = registerBlock("copper_blockz_2",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.COPPER)
                    .strength(0.3f, 6.0f)

                    .requiresCorrectToolForDrops()
            )
    );

    // Group copper variants for easy iteration
    public static final DeferredBlock<?>[] COPPER_VARIANTS = new DeferredBlock<?>[]{
            COPPER_BLOCKZ_0, COPPER_BLOCKZ_1, COPPER_BLOCKZ_2
    };

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
