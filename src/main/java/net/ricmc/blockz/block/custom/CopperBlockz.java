package net.ricmc.blockz.block.custom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.HitResult;
import java.util.HashMap;
import java.util.Map;

public class CopperBlockz extends Block {
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 2);

    public CopperBlockz() {
        super(BlockBehaviour.Properties.of()
                .strength(3.0F, 6.0F)
                .sound(SoundType.COPPER)
                .requiresCorrectToolForDrops()
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(this);

        // Convert blockstate -> Map<String, String>
        Map<String, String> props = new HashMap<>();
        for (Map.Entry<Property<?>, Comparable<?>> entry : state.getValues().entrySet()) {
            Property<?> property = entry.getKey();
            Comparable<?> value = entry.getValue();

            // Safe generic cast
            addPropertyToMap(props, property, value);
        }

        // Store properties inside the item
        stack.set(DataComponents.BLOCK_STATE, new BlockItemStateProperties(props));
        return stack;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addPropertyToMap(Map<String, String> props, Property property, Comparable value) {
        props.put(property.getName(), property.getName(value));
    }

    public InteractionResult cycleVariant(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide) {
            int current = state.getValue(VARIANT);
            int next = (current + 1) % 3;
            level.setBlock(pos, state.setValue(VARIANT, next), 3);
        }
        return InteractionResult.SUCCESS;
    }


}
