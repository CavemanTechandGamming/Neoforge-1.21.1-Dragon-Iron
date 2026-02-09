package com.caveman.cavemansdragoniron.datagen;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import com.caveman.cavemansdragoniron.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CavemansDragonIron.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DRAGON_IRON_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DRAGON_IRON_BLOCK.get());

        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.DRAGON_IRON_BLOCK.get());

        tag(BlockTags.DRAGON_IMMUNE)
                .add(ModBlocks.DRAGON_IRON_BLOCK.get());

        tag(BlockTags.WITHER_IMMUNE)
                .add(ModBlocks.DRAGON_IRON_BLOCK.get());



    }
}
