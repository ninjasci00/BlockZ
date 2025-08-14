package net.ricmc.blockz.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class BazeBlock extends Block {

    // Define the MODE property (0 to 2)
    public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, 2);

    public BazeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MODE, 0));
    }

    // Required to define which block states your block has
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MODE);
    }

    // Optional: Example behavior based on mode
    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        int mode = state.getValue(MODE);
        // Do something depending on mode
        // e.g., change block behavior, interact differently, etc.
    }

    // Optional: Example method to get mode for rendering or logic
    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        int mode = state.getValue(MODE);
        return mode * 2; // Example: light level changes with mode
    }
}
