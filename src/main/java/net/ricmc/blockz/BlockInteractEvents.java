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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockInteractEvents {

    // Map each block “group” to its variant items
    static final Map<String, List<Item>> VARIANTS_MAP = new HashMap<>();

    static {
        // Copper variants
        VARIANTS_MAP.put("copper", Arrays.asList(
                ModBlocks.COPPER_BLOCKZ_0.get().asItem(),
                ModBlocks.COPPER_BLOCKZ_1.get().asItem(),
                ModBlocks.COPPER_BLOCKZ_2.get().asItem()
        ));

        // Add other block sets here similarly
        // VARIANTS_MAP.put("tin", Arrays.asList(ModBlocks.TIN_0.get().asItem(), ...));
    }

    private BlockInteractEvents() {}

    private static void cycleVariant(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof BlockItem blockItem)) return;

        // Find which variant list this block belongs to
        List<Item> variants = VARIANTS_MAP.values().stream()
                .filter(list -> list.contains(stack.getItem()))
                .findFirst()
                .orElse(null);

        if (variants == null || variants.isEmpty()) return;

        int currentIndex = variants.indexOf(stack.getItem());
        int nextIndex = (currentIndex + 1) % variants.size();
        Item nextItem = variants.get(nextIndex);

        // Preserve the original stack count
        int originalCount = stack.getCount();
        ItemStack newStack = new ItemStack(nextItem, originalCount);
        player.setItemInHand(hand, newStack);
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
