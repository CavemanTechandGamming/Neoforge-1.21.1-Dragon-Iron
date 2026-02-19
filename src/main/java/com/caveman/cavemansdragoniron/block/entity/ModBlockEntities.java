package com.caveman.cavemansdragoniron.block.entity;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import com.caveman.cavemansdragoniron.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CavemansDragonIron.MOD_ID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<BlockEntityType<?>, BlockEntityType<DragonIronFurnaceBlockEntity>> DRAGON_IRON_FURNACE =
            BLOCK_ENTITIES.register("dragon_iron_furnace", () ->
                    BlockEntityType.Builder.of(DragonIronFurnaceBlockEntity::new, ModBlocks.DRAGON_IRON_FURNACE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
