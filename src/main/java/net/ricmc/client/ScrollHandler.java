package your.mod.package;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.ricmc.blockz.BlockzMod;
import net.ricmc.blockz.item.custom.BazeBlockItem;

@Mod.EventBusSubscriber(modid = BlockzMod.MOD_ID, value = Dist.CLIENT)
public class BazeBlockScrollHandler {

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null && mc.player.isCrouching()) {
            ItemStack heldItem = mc.player.getMainHandItem();

            if (heldItem.getItem() instanceof BazeBlockItem bazeItem) {
                double scrollDelta = event.getScrollDelta();

                if (scrollDelta > 0) {
                    BazeBlockItem.cycleMode(heldItem, true);
                } else if (scrollDelta < 0) {
                    BazeBlockItem.cycleMode(heldItem, false);
                }

                int currentMode = BazeBlockItem.getMode(heldItem);
                mc.player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("Mode: " + currentMode), true
                );

                event.setCanceled(true); // Prevent hotbar from switching
            }
        }
    }
}
