package com.caveman.cavemansdragoniron.block.entity;

import com.caveman.cavemansdragoniron.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public class DragonIronFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_RESULT = 1;
    public static final int DATA_COOKING_PROGRESS = 0;
    public static final int DATA_COOKING_TOTAL_TIME = 1;

    private static final int COOK_SPEED_DIVISOR = 2;

    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private int cookingProgress;
    private int cookingTotalTime;
    private final ContainerData data = new SimpleContainerData(2) {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_COOKING_PROGRESS -> DragonIronFurnaceBlockEntity.this.cookingProgress;
                case DATA_COOKING_TOTAL_TIME -> DragonIronFurnaceBlockEntity.this.cookingTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case DATA_COOKING_PROGRESS -> DragonIronFurnaceBlockEntity.this.cookingProgress = value;
                case DATA_COOKING_TOTAL_TIME -> DragonIronFurnaceBlockEntity.this.cookingTotalTime = value;
            }
        }
    };

    public DragonIronFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRAGON_IRON_FURNACE.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.cavemansdragoniron.dragon_iron_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new com.caveman.cavemansdragoniron.menu.DragonIronFurnaceMenu(containerId, playerInventory, this, getData());
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        for (int i = 0; i < items.size() && i < stacks.size(); i++) {
            items.set(i, stacks.get(i));
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[]{SLOT_INPUT, SLOT_RESULT};
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction direction) {
        if (slot != SLOT_INPUT) return false;
        return level != null && getRecipeFor(stack).isPresent();
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return slot == SLOT_RESULT;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot != SLOT_INPUT) return false;
        return getRecipeFor(stack).isPresent();
    }

    @SuppressWarnings("unchecked")
    private Optional<RecipeHolder<net.minecraft.world.item.crafting.AbstractCookingRecipe>> getRecipeFor(ItemStack stack) {
        if (level == null || stack.isEmpty()) return Optional.empty();
        for (RecipeHolder<net.minecraft.world.item.crafting.SmeltingRecipe> holder : level.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING)) {
            if (holder.value().getIngredients().isEmpty()) continue;
            if (holder.value().getIngredients().get(0).test(stack)) {
                return Optional.of((RecipeHolder<net.minecraft.world.item.crafting.AbstractCookingRecipe>) (RecipeHolder<?>) holder);
            }
        }
        return Optional.empty();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DragonIronFurnaceBlockEntity blockEntity) {
        boolean wasLit = state.getValue(BlockStateProperties.LIT);
        boolean changed = false;

        ItemStack inputStack = blockEntity.items.get(SLOT_INPUT);
        Optional<RecipeHolder<net.minecraft.world.item.crafting.AbstractCookingRecipe>> recipeOpt = blockEntity.getRecipeFor(inputStack);

        if (recipeOpt.isPresent() && blockEntity.canSmelt(recipeOpt.get())) {
            RecipeHolder<net.minecraft.world.item.crafting.AbstractCookingRecipe> recipeHolder = recipeOpt.get();
            int totalTime = recipeHolder.value().getCookingTime();
            int halfTime = Math.max(1, totalTime / COOK_SPEED_DIVISOR);

            blockEntity.cookingTotalTime = halfTime;
            blockEntity.cookingProgress++;

            if (blockEntity.cookingProgress >= halfTime) {
                blockEntity.cookingProgress = 0;
                blockEntity.smeltItem(recipeHolder);
                changed = true;
            }
            changed = true;

            if (!wasLit) {
                level.setBlock(pos, state.setValue(BlockStateProperties.LIT, true), 3);
            }
        } else {
            if (blockEntity.cookingProgress > 0) {
                blockEntity.cookingProgress = 0;
                changed = true;
            }
            if (wasLit) {
                level.setBlock(pos, state.setValue(BlockStateProperties.LIT, false), 3);
                changed = true;
            }
        }

        if (changed) {
            blockEntity.setChanged();
        }
    }

    private boolean canSmelt(RecipeHolder<net.minecraft.world.item.crafting.AbstractCookingRecipe> recipe) {
        if (items.get(SLOT_INPUT).isEmpty()) return false;
        ItemStack result = level != null ? recipe.value().getResultItem(level.registryAccess()) : ItemStack.EMPTY;
        if (result.isEmpty()) return false;
        ItemStack currentResult = items.get(SLOT_RESULT);
        if (currentResult.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(currentResult, result)) return false;
        return currentResult.getCount() + result.getCount() <= result.getMaxStackSize();
    }

    public ContainerData getData() {
        return data;
    }

    private void smeltItem(RecipeHolder<net.minecraft.world.item.crafting.AbstractCookingRecipe> recipe) {
        ItemStack input = items.get(SLOT_INPUT);
        ItemStack result = level != null ? recipe.value().getResultItem(level.registryAccess()) : ItemStack.EMPTY;
        ItemStack currentResult = items.get(SLOT_RESULT);

        if (currentResult.isEmpty()) {
            items.set(SLOT_RESULT, result.copy());
        } else if (ItemStack.isSameItemSameComponents(currentResult, result)) {
            currentResult.grow(result.getCount());
        }

        input.shrink(1);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("CookingProgress", cookingProgress);
        tag.putInt("CookingTotalTime", cookingTotalTime);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        cookingProgress = tag.getInt("CookingProgress");
        cookingTotalTime = tag.getInt("CookingTotalTime");
        ContainerHelper.loadAllItems(tag, items, registries);
    }
}
