package com.caveman.cavemansdragoniron;

import com.caveman.cavemansdragoniron.client.gui.DragonIronFurnaceScreen;
import com.caveman.cavemansdragoniron.client.gui.ModConfigScreen;
import com.caveman.cavemansdragoniron.menu.ModMenuTypes;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = CavemansDragonIron.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = CavemansDragonIron.MOD_ID, value = Dist.CLIENT)
public class ExampleModClient {
    public ExampleModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> new ModConfigScreen(parent));
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        CavemansDragonIron.LOGGER.info("HELLO FROM CLIENT SETUP");
        CavemansDragonIron.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.DRAGON_IRON_FURNACE.get(), DragonIronFurnaceScreen::new);
    }
}
