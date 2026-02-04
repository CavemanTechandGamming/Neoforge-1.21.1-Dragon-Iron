package com.caveman.cavemansdragoniron.item;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import com.caveman.cavemansdragoniron.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CavemansDragonIron.MOD_ID);

    public static final Supplier<CreativeModeTab> CAVEMANS_DRAGON_IRON_ITEMS_TAB = CREATIVE_MODE_TAB.register("cavemans_dragon_iron_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.DRAGON_IRON_BLOCK))
                    .title(Component.translatable("creativetab.cavemansdragoniron.cavemans_dragon_iron_items"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.DRAGON_IRON_NUGGET);
                        output.accept(ModItems.DRAGON_IRON_INGOT);
                        output.accept(ModBlocks.DRAGON_IRON_BLOCK);
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
