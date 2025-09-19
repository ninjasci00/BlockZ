package net.ricmc.blockz.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ricmc.blockz.BlockzMod;
import net.ricmc.blockz.item.custom.CobbleCrafterItem;
import net.ricmc.blockz.item.custom.OmniSpadeItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlockzMod.MOD_ID);

    public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> OMNI_SPADE = ITEMS.register("omni_spade",
        () -> new OmniSpadeItem(new Item.Properties().durability(100)));

    public static final DeferredItem<Item> COBBLE_CRAFTER = ITEMS.register("cobble_crafter",
        () -> new CobbleCrafterItem(new Item.Properties().durability(100)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
