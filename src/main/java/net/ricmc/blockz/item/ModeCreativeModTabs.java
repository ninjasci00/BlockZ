package net.ricmc.blockz.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ricmc.blockz.BlockzMod;
import net.ricmc.blockz.block.ModBlocks;

import java.util.function.Supplier;

public class ModeCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlockzMod.MOD_ID);

    public static final Supplier<CreativeModeTab> BLOCKZ_ITEMS_TAB = CREATIVE_MODE_TAB.register("blockz_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get()))
                    .title(Component.translatable("creativetab.blockzmod.blockz_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BISMUTH);
                        output.accept(ModItems.RAW_BISMUTH);
                        output.accept(ModItems.OMNI_SPADE);
                        output.accept(ModItems.COBBLE_CRAFTER);



    }).build());

    public static final Supplier<CreativeModeTab> BLOCKZ_BLOCK_TAB = CREATIVE_MODE_TAB.register("blockz_block_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(BlockzMod.MOD_ID, "blockz_items_tab"))
                    .title(Component.translatable("creativetab.blockzmod.blockz_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.SMOOTHDIRTLIGHT_BLOCK);
                        output.accept(ModBlocks.SMOOTHDIRTDARK_BLOCK);
                        output.accept(ModBlocks.EXCHANGE_BLOCK);

    }).build());




    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
