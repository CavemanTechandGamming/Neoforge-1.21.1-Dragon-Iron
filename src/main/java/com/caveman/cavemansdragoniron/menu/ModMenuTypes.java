package com.caveman.cavemansdragoniron.menu;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CavemansDragonIron.MOD_ID);

    public static final Supplier<MenuType<DragonIronFurnaceMenu>> DRAGON_IRON_FURNACE =
            MENUS.register("dragon_iron_furnace", () ->
                    IMenuTypeExtension.create(DragonIronFurnaceMenu::fromNetwork));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
