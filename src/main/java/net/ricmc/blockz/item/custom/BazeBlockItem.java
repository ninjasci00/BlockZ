package net.ricmc.blockz.item.custom;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.ricmc.blockz.block.custom.BazeBlock;

public class BazeBlockItem extends BlockItem {

    public static final String MODE_KEY = "Mode";
    public static final int MAX_MODES = 3;

    public BazeBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static int getMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.getInt(MODE_KEY);
    }

    public static void setMode(ItemStack stack, int mode) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(MODE_KEY, mode);
    }

    public static void cycleMode(ItemStack stack, boolean forward) {
        int mode = getMode(stack);
        if (forward) {
            mode = (mode + 1) % MAX_MODES;
        } else {
            mode = (mode - 1 + MAX_MODES) % MAX_MODES;
        }
        setMode(stack, mode);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        ItemStack stack = context.getItemInHand();
        int mode = getMode(stack);
        BlockState newState = state.setValue(BazeBlock.MODE, mode);
        return context.getLevel().setBlock(context.getClickedPos(), newState, 3);
    }
}
