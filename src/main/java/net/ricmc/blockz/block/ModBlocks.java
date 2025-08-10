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
import net.ricmc.blockz.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlockzMod.MOD_ID);

    public static final DeferredBlock<Block> TERRA_BLOCK = registerBlock("terra_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(1f)
            )
    );
    public static final DeferredBlock<Block> SMOOTHDIRT_BLOCK = registerBlock("smoothdirt_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );
    public static final DeferredBlock<Block> SMOOTHDIRT3_BLOCK = registerBlock("smoothdirt3_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );
    public static final DeferredBlock<Block> SMOOTHDIRT4_BLOCK = registerBlock("smoothdirt4_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );public static final DeferredBlock<Block> SMOOTHDIRT5_BLOCK = registerBlock("smoothdirt5_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );public static final DeferredBlock<Block> SMOOTHDIRT6_BLOCK = registerBlock("smoothdirt6_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );public static final DeferredBlock<Block> SMOOTHDIRT7_BLOCK = registerBlock("smoothdirt7_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ROOTED_DIRT)
                    .strength(0.1f)
                    .speedFactor(1.2F)
            )
    );
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
