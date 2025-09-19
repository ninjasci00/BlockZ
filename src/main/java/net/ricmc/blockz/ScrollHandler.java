package net.ricmc.blockz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.InputEvent;

import java.util.List;

@net.neoforged.fml.common.EventBusSubscriber(modid = "blockzmod", value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ScrollHandler {

    @net.neoforged.bus.api.SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        // Only trigger if SHIFT is held
        if (!player.isShiftKeyDown()) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof BlockItem blockItem)) return;

        Block block = blockItem.getBlock();
        List<Item> variants = BlockInteractEvents.VARIANTS_MAP.get(block.getClass());
        if (variants == null || variants.isEmpty()) return;

        int currentIndex = variants.indexOf(stack.getItem());
        if (currentIndex == -1) return;

        // Scroll direction: event.getScrollDelta() > 0 → next variant
        int nextIndex = currentIndex + (event.getScrollDeltaX() > 0 ? 1 : -1);
        nextIndex = (nextIndex + variants.size()) % variants.size(); // Wrap around
        Item nextItem = variants.get(nextIndex);

        player.setItemInHand(player.getUsedItemHand(), new ItemStack(nextItem));

        // Cancel default scroll behavior
        event.setCanceled(true);
    }
}
