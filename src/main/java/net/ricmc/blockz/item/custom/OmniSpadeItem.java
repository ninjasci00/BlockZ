package net.ricmc.blockz.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.ricmc.blockz.block.ModBlocks;

import java.util.*;

public class OmniSpadeItem extends Item {

    private static final int MAX_BLOCKS = 30; // safety limit for flood fill

    // Forward conversion maps
    private static final Map<Block, Block> DIRT_MAP;
    private static final Map<Block, Block> STONE_MAP;
    private static final Map<Block, Block> METAL_MAP;

    // Reverse conversion maps (simplified — one smoothed block per category)
    private static final Map<Block, Block> REVERSE_DIRT_MAP;
    private static final Map<Block, Block> REVERSE_STONE_MAP;
    private static final Map<Block, Block> REVERSE_METAL_MAP;

    static {
        // Forward mappings
        Map<Block, Block> dirtMap = new HashMap<>();
        dirtMap.put(Blocks.DIRT, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get());
        dirtMap.put(Blocks.GRASS_BLOCK, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get());
        dirtMap.put(Blocks.DIRT_PATH, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get());
        dirtMap.put(Blocks.COARSE_DIRT, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get());
        DIRT_MAP = Collections.unmodifiableMap(dirtMap);

        Map<Block, Block> stoneMap = new HashMap<>();
        stoneMap.put(Blocks.STONE, ModBlocks.SMOOTHCOBBLESTONELIGHT_BLOCK.get());
        stoneMap.put(Blocks.COBBLESTONE, ModBlocks.SMOOTHCOBBLESTONELIGHT_BLOCK.get());
        stoneMap.put(Blocks.ANDESITE, ModBlocks.SMOOTHCOBBLESTONELIGHT_BLOCK.get());
        STONE_MAP = Collections.unmodifiableMap(stoneMap);

        Map<Block, Block> metalMap = new HashMap<>();
        metalMap.put(Blocks.IRON_ORE, Blocks.IRON_BLOCK);
        metalMap.put(Blocks.GOLD_ORE, Blocks.GOLD_BLOCK);
        METAL_MAP = Collections.unmodifiableMap(metalMap);

        // Reverse mappings — simplified (all smoothed → one base)
        REVERSE_DIRT_MAP = Map.of(
                ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(), Blocks.DIRT
        );

        REVERSE_STONE_MAP = Map.of(
                ModBlocks.SMOOTHCOBBLESTONELIGHT_BLOCK.get(), Blocks.COBBLESTONE
        );

        REVERSE_METAL_MAP = Map.of(
                Blocks.IRON_BLOCK, Blocks.IRON_ORE,
                Blocks.GOLD_BLOCK, Blocks.GOLD_ORE
        );
    }

    public OmniSpadeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos startPos = context.getClickedPos();
        Block clickedBlock = level.getBlockState(startPos).getBlock();

        Map<Block, Block> conversionMap = getMapForBlock(clickedBlock);
        if (conversionMap == null) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            Block replacement = conversionMap.get(clickedBlock);
            if (replacement == null) return InteractionResult.PASS;

            int changed;

            if (context.getPlayer().isShiftKeyDown()) {
                // Single block mode
                level.setBlockAndUpdate(startPos, replacement.defaultBlockState());
                changed = 1;
            } else {
                // 5x5 area replace based on clicked face
                Direction faceClicked = context.getClickedFace();
                changed = replace5x5Area(level, startPos, clickedBlock, replacement, faceClicked);
            }


            level.playSound(null, startPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
        }

        return InteractionResult.SUCCESS;
    }

    /**
     * Picks the correct map for forward or reverse conversion.
     */
    private Map<Block, Block> getMapForBlock(Block block) {
        if (DIRT_MAP.containsKey(block)) return DIRT_MAP;
        if (STONE_MAP.containsKey(block)) return STONE_MAP;
        if (METAL_MAP.containsKey(block)) return METAL_MAP;

        if (REVERSE_DIRT_MAP.containsKey(block)) return REVERSE_DIRT_MAP;
        if (REVERSE_STONE_MAP.containsKey(block)) return REVERSE_STONE_MAP;
        if (REVERSE_METAL_MAP.containsKey(block)) return REVERSE_METAL_MAP;

        return null;
    }

    /**
     * Replaces connected blocks of targetBlock with replacementBlock, up to MAX_BLOCKS.
     */
    private int replace5x5Area(Level level, BlockPos startPos, Block targetBlock, Block replacementBlock, Direction faceClicked) {
        int changedCount = 0;
        final int RADIUS = 3; // 5x5 means radius 2

        // Depending on faceClicked, define which axes to span
        // For top/bottom: span X,Z at fixed Y
        // For walls: span X,Y or Z,Y at fixed Z or X respectively

        for (int dx = -RADIUS; dx <= RADIUS; dx++) {
            for (int dy = -RADIUS; dy <= RADIUS; dy++) {

                BlockPos pos;

                switch (faceClicked) {
                    case UP: // top face, horizontal square on X,Z plane
                        pos = startPos.offset(dx, 0, dy);
                        break;
                    case DOWN: // bottom face, same as top
                        pos = startPos.offset(dx, 0, dy);
                        break;
                    case NORTH: // north face, square on X,Y at fixed Z-1
                        pos = new BlockPos(startPos.getX() + dx, startPos.getY() + dy, startPos.getZ());
                        break;
                    case SOUTH: // south face, square on X,Y at fixed Z+1
                        pos = new BlockPos(startPos.getX() + dx, startPos.getY() + dy, startPos.getZ());
                        break;
                    case WEST: // west face, square on Z,Y at fixed X-1
                        pos = new BlockPos(startPos.getX(), startPos.getY() + dy, startPos.getZ() + dx);
                        break;
                    case EAST: // east face, square on Z,Y at fixed X+1
                        pos = new BlockPos(startPos.getX(), startPos.getY() + dy, startPos.getZ() + dx);
                        break;
                    default:
                        // fallback to horizontal plane
                        pos = startPos.offset(dx, 0, dy);
                }

                BlockState state = level.getBlockState(pos);
                if (state.getBlock() == targetBlock) {
                    level.setBlockAndUpdate(pos, replacementBlock.defaultBlockState());
                    changedCount++;
                    if (changedCount >= MAX_BLOCKS) {
                        return changedCount; // stop if max reached
                    }
                }
            }
        }

        return changedCount;
    }



}