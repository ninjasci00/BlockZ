package net.ricmc.blockz.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CobbleCrafterItem extends Item {

    private static final int FLAT_RADIUS = 3; // 7x7 area
    private static final int CUBE_RADIUS = 4; // 9x9 cube (centered)
    private static final int CUBE_HEIGHT = 9;
    private static final int CUBE_BLOCK_COUNT = 386; // Precomputed for 9x9x9 hollow cube

    public CobbleCrafterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();

        if (level.isClientSide) return InteractionResult.SUCCESS;

        Player player = context.getPlayer();
        if (player == null) return InteractionResult.FAIL;

        int cobblePlaced = 0;

        if (player.isShiftKeyDown()) {
            // === PRE-CHECK INVENTORY ===
            int available = countCobbleInInventory(player);

            if (available < CUBE_BLOCK_COUNT) {
                int missing = CUBE_BLOCK_COUNT - available;
                player.sendSystemMessage(Component.literal("Not enough cobblestone! You need " + missing + " more."));
                player.playSound(SoundEvents.ANVIL_BREAK, 1.0F, 1.0F);
                return InteractionResult.FAIL;
            }

            // === BUILD 9x9x9 HOLLOW CUBE ===
            for (int dx = -CUBE_RADIUS; dx <= CUBE_RADIUS; dx++) {
                for (int dz = -CUBE_RADIUS; dz <= CUBE_RADIUS; dz++) {
                    for (int dy = 0; dy < CUBE_HEIGHT; dy++) {
                        boolean isEdge = dx == -CUBE_RADIUS || dx == CUBE_RADIUS ||
                                dz == -CUBE_RADIUS || dz == CUBE_RADIUS ||
                                dy == 0 || dy == CUBE_HEIGHT - 1;

                        if (isEdge) {
                            BlockPos targetPos = clickedPos.offset(dx, dy, dz);
                            BlockState state = level.getBlockState(targetPos);

                            if (canPlaceCobble(state) && removeCobbleFromInventory(player)) {
                                level.setBlockAndUpdate(targetPos, Blocks.COBBLESTONE.defaultBlockState());
                                cobblePlaced++;
                            }
                        }
                    }
                }
            }

            if (cobblePlaced > 0) {
                level.playSound(null, clickedPos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }

        } else {
            // === BUILD 7x7 FLAT AREA ===
            for (int dx = -FLAT_RADIUS; dx <= FLAT_RADIUS; dx++) {
                for (int dz = -FLAT_RADIUS; dz <= FLAT_RADIUS; dz++) {
                    BlockPos targetPos = clickedPos.offset(dx, 0, dz);
                    BlockState state = level.getBlockState(targetPos);

                    if (canPlaceCobble(state) && removeCobbleFromInventory(player)) {
                        level.setBlockAndUpdate(targetPos, Blocks.COBBLESTONE.defaultBlockState());
                        cobblePlaced++;
                    }
                }
            }

            if (cobblePlaced > 0) {
                level.playSound(null, clickedPos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

    // === Helper Methods ===

    private boolean canPlaceCobble(BlockState state) {
        return state.isAir()
                || (!state.getBlock().equals(Blocks.BEDROCK) && !state.canOcclude());
    }

    private boolean removeCobbleFromInventory(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.is(Blocks.COBBLESTONE.asItem())) {
                stack.shrink(1); // Consume 1 cobble
                return true;
            }
        }
        return false; // No cobble found
    }

    private int countCobbleInInventory(Player player) {
        int total = 0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.is(Blocks.COBBLESTONE.asItem())) {
                total += stack.getCount();
            }
        }
        return total;
    }
}
