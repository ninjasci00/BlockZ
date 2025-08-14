package net.ricmc.blockz.item.custom;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.ricmc.blockz.block.custom.BazeBlock;

public class BazeBlockItem extends BlockItem {

    public static final String MODE_KEY = "Mode";
    public static final int MAX_MODES = 3;

    public BazeBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    // Safely get or create NBT tag manually
    private static CompoundTag getTagSafe(ItemStack stack) {
        CompoundTag tag;
        try {
            tag = (CompoundTag) stack.getClass()
                    .getMethod("getTag")
                    .invoke(stack);
        } catch (Exception e) {
            tag = null;
        }

        if (tag == null) {
            tag = new CompoundTag();
            try {
                stack.getClass()
                        .getMethod("setTag", CompoundTag.class)
                        .invoke(stack, tag);
            } catch (Exception ignored) {}
        }

        return tag;
    }

    // Get current mode
    public static int getMode(ItemStack stack) {
        return getTagSafe(stack).getInt(MODE_KEY);
    }

    // Set mode
    public static void setMode(ItemStack stack, int mode) {
        getTagSafe(stack).putInt(MODE_KEY, mode);
    }

    // Cycle mode forward/backward
    public static void cycleMode(ItemStack stack, boolean forward) {
        int mode = getMode(stack);
        if (forward) mode = (mode + 1) % MAX_MODES;
        else mode = (mode - 1 + MAX_MODES) % MAX_MODES;
        setMode(stack, mode);
    }

    // Apply mode when placing block
    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        ItemStack stack = context.getItemInHand();
        int mode = getMode(stack);

        BlockState newState = state.setValue(BazeBlock.MODE, mode); // Ensure BazeBlock.MODE exists
        return context.getLevel().setBlock(context.getClickedPos(), newState, 3);
    }
}
