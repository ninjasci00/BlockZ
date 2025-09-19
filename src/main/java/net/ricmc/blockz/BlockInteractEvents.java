package net.ricmc.blockz;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.ricmc.blockz.block.ModBlocks;
import net.ricmc.blockz.block.custom.CopperBlockz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BlockInteractEvents {

    // Map each block class to its variant items
    static final Map<Class<? extends Block>, List<Item>> VARIANTS_MAP = new HashMap<>();

    static {
        // Add all variant lists here
        VARIANTS_MAP.put(net.ricmc.blockz.block.custom.CopperBlockz.class, List.of(
                ModBlocks.COPPER_BLOCKZ_0.get().asItem(),
                ModBlocks.COPPER_BLOCKZ_1.get().asItem(),
                ModBlocks.COPPER_BLOCKZ_2.get().asItem()
        ));

        // Example: add another block with variants
        // VARIANTS_MAP.put(AnotherBlock.class, List.of(
        //      ModBlocks.ANOTHER_BLOCK_0.get().asItem(),
        //      ModBlocks.ANOTHER_BLOCK_1.get().asItem()
        // ));
    }

    private BlockInteractEvents() {}

    private static void cycleVariant(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!(stack.getItem() instanceof BlockItem blockItem)) return;

        Block block = blockItem.getBlock();
        List<Item> variants = VARIANTS_MAP.get(block.getClass());
        if (variants == null || variants.isEmpty()) return;

        int currentIndex = variants.indexOf(stack.getItem());
        if (currentIndex == -1) return;

        int nextIndex = (currentIndex + 1) % variants.size();
        Item nextItem = variants.get(nextIndex);

        player.setItemInHand(hand, new ItemStack(nextItem));
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (!player.isShiftKeyDown()) return;

        cycleVariant(player, event.getHand());

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (!player.isShiftKeyDown()) return;

        cycleVariant(player, event.getHand());

        // Prevent normal block interaction (placing/using)
        event.setUseBlock(TriState.FALSE);
        event.setUseItem(TriState.FALSE);
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }
}

