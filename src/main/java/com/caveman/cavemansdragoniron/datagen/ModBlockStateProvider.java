package com.caveman.cavemansdragoniron.datagen;

import com.caveman.cavemansdragoniron.CavemansDragonIron;
import com.caveman.cavemansdragoniron.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CavemansDragonIron.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.DRAGON_IRON_BLOCK);

        stairsBlock(ModBlocks.DRAGON_IRON_STAIRS.get(), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()));
        slabBlock(ModBlocks.DRAGON_IRON_SLAB.get(), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()));

        buttonBlock(ModBlocks.DRAGON_IRON_BUTTON.get(), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()));
        pressurePlateBlock(ModBlocks.DRAGON_IRON_PRESSURE_PLATE.get(), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()));

        fenceBlock(ModBlocks.DRAGON_IRON_FENCE.get(), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()));
        fenceGateBlock(ModBlocks.DRAGON_IRON_FENCE_GATE.get(), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()));
        wallBlock(ModBlocks.DRAGON_IRON_WALL.get(), blockTexture(ModBlocks.DRAGON_IRON_BLOCK.get()));

        doorBlockWithRenderType(ModBlocks.DRAGON_IRON_DOOR.get(), modLoc("block/dragon_iron_door_bottom"), modLoc("block/dragon_iron_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.DRAGON_IRON_TRAP_DOOR.get(), modLoc("block/dragon_iron_trap_door"), true, "cutout");

        blockItem(ModBlocks.DRAGON_IRON_STAIRS);
        blockItem(ModBlocks.DRAGON_IRON_SLAB);
        blockItem(ModBlocks.DRAGON_IRON_PRESSURE_PLATE);
        blockItem(ModBlocks.DRAGON_IRON_FENCE_GATE);
        blockItem(ModBlocks.DRAGON_IRON_TRAP_DOOR, "_bottom");


    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("cavemansdragoniron:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("cavemansdragoniron:block/" + deferredBlock.getId().getPath() + appendix));
    }
}