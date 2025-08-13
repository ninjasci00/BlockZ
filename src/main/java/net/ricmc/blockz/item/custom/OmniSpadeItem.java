package net.ricmc.blockz.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.ricmc.blockz.block.ModBlocks;

import java.util.Map;

public class OmniSpadeItem extends Item {
    private static final Map<Block, Block> OMNISPADE_MAP =
            Map.of(
                    Blocks.DIRT, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(),
                    Blocks.GRASS_BLOCK, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(),
                    Blocks.DIRT_PATH, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(),
                    Blocks.COARSE_DIRT, ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(),
                    ModBlocks.SMOOTHDIRTLIGHT_BLOCK.get(), Blocks.DIRT

            );

    public OmniSpadeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if(OMNISPADE_MAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(), OMNISPADE_MAP.get(clickedBlock).defaultBlockState());

                context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), context.getPlayer(),
                        item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                level.playSound(null, context.getClickedPos(), SoundEvents.HOE_TILL, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }
}