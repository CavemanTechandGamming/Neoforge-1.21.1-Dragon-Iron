package com.caveman.cavemansdragoniron.datagen;

import com.caveman.cavemansdragoniron.block.ModBlocks;
import com.caveman.cavemansdragoniron.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        //Blocks that drop themselves.
        dropSelf(ModBlocks.DRAGON_IRON_BLOCK.get());
        dropSelf(ModBlocks.FANCY_DRAGON_IRON_BLOCK.get());
        dropSelf(ModBlocks.DRAGON_GLASS_BLOCK.get());
        dropSelf(ModBlocks.DRAGON_GLASS_PANE.get());
        dropSelf(ModBlocks.DRAGON_IRON_STAIRS.get());
        dropSelf(ModBlocks.DRAGON_IRON_BUTTON.get());
        dropSelf(ModBlocks.DRAGON_IRON_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.DRAGON_IRON_FENCE.get());
        dropSelf(ModBlocks.DRAGON_IRON_FENCE_GATE.get());
        dropSelf(ModBlocks.DRAGON_IRON_WALL.get());
        dropSelf(ModBlocks.DRAGON_IRON_TRAP_DOOR.get());
        dropSelf(ModBlocks.DRAGON_IRON_FURNACE.get());

        //How to deal with doors because they're weird.
        add(ModBlocks.DRAGON_IRON_DOOR.get(), block -> createDoorTable(ModBlocks.DRAGON_IRON_DOOR.get()));

        //How to deal with slabs because they're weird.
        add(ModBlocks.DRAGON_IRON_SLAB.get(), block -> createSlabItemTable(ModBlocks.DRAGON_IRON_SLAB.get()));

        //An example of an ore block that only drops one ore.
        //add(ModBlocks.BISMUTH_ORE.get(),
                //block -> createOreDrop(ModBlocks.BISMUTH_ORE.get(), ModItems.RAW_BISMUTH.get()));

        //And an example of an ore block that drops multiple ore.
        //add(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(),
                //block -> createMultipleOreDrops(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), ModItems.RAW_BISMUTH.get(), 2, 5));

    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
