package com.caveman.cavemansdragoniron.datagen;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import com.caveman.cavemansdragoniron.item.ModItems;
import com.caveman.cavemansdragoniron.loot.AddItemWithConfigChanceModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CavemansDragonIron.MOD_ID);
    }

    @Override
    protected void start() {
        this.add("dragon_iron_nugget_from_enderman",
                        new AddItemWithConfigChanceModifier(new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/enderman")).build()
                        }, ModItems.DRAGON_IRON_NUGGET.get()));
    }
}