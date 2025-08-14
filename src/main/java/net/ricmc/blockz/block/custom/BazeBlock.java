package net.ricmc.blockz.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.ricmc.blockz.item.custom.BazeBlockItem;

public class BazeBlock extends Block {

    public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, BazeBlockItem.MAX_MODES - 1);

    public BazeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MODE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }
}
