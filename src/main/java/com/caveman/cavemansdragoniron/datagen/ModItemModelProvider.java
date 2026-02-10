package com.caveman.cavemansdragoniron.datagen;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import com.caveman.cavemansdragoniron.block.ModBlocks;
import com.caveman.cavemansdragoniron.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CavemansDragonIron.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // ===== Basic items =====
        basicItem(ModItems.DRAGON_IRON_INGOT.get());
        basicItem(ModItems.DRAGON_IRON_NUGGET.get());

        // ===== Tools & weapons =====
        handheldItem(ModItems.DRAGON_IRON_AXE);
        handheldItem(ModItems.DRAGON_IRON_HOE);
        handheldItem(ModItems.DRAGON_IRON_PICKAXE);
        handheldItem(ModItems.DRAGON_IRON_HAMMER);
        handheldItem(ModItems.DRAGON_IRON_SHOVEL);
        handheldItem(ModItems.DRAGON_IRON_SWORD);

        // ===== Block items =====
        basicItem(ModBlocks.DRAGON_IRON_DOOR.asItem());

        // ===== Block variants (use block texture) =====
        buttonItem(ModBlocks.DRAGON_IRON_BUTTON, ModBlocks.DRAGON_IRON_BLOCK);
        fenceItem(ModBlocks.DRAGON_IRON_FENCE, ModBlocks.DRAGON_IRON_BLOCK);
        wallItem(ModBlocks.DRAGON_IRON_WALL, ModBlocks.DRAGON_IRON_BLOCK);


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

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CavemansDragonIron.MOD_ID,"item/" + item.getId().getPath()));
    }
}
