package net.ricmc.blockz.item.custom.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.ricmc.blockz.item.custom.BazeBlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class BazeBlockScrollHandler {

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        Player player = mc.player;
        ItemStack stack = player.getMainHandItem();

        if (!(stack.getItem() instanceof BazeBlockItem)) return;

        double scrollDelta = event.getScrollDelta();
        boolean forward = scrollDelta > 0;

        // Cycle the mode using BazeBlockItem's built-in method
        BazeBlockItem.cycleMode(stack, forward);

        // Optional: show current mode as client message
        player.displayClientMessage(
                new net.minecraft.network.chat.TextComponent("Mode: " + BazeBlockItem.getMode(stack)),
                true
        );

        // Cancel default scroll action to prevent conflicts
        event.setCanceled(true);
    }
}
