package com.caveman.cavemansdragoniron.datagen;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import com.caveman.cavemansdragoniron.block.ModBlocks;
import com.caveman.cavemansdragoniron.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CavemansDragonIron.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.DRAGON_IRON_NUGGET.get());
        basicItem(ModItems.DRAGON_IRON_INGOT.get());

        buttonItem(ModBlocks.DRAGON_IRON_BUTTON, ModBlocks.DRAGON_IRON_BLOCK);
        fenceItem(ModBlocks.DRAGON_IRON_FENCE, ModBlocks.DRAGON_IRON_BLOCK);
        wallItem(ModBlocks.DRAGON_IRON_WALL, ModBlocks.DRAGON_IRON_BLOCK);

        basicItem(ModBlocks.DRAGON_IRON_DOOR.asItem());

    }

    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(CavemansDragonIron.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(CavemansDragonIron.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(CavemansDragonIron.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
}
