package com.caveman.cavemansdragoniron.item;

import com.caveman.cavemansdragoniron.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier DRAGON_IRON = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_DRAGON_IRON_TOOL,
            5500, 24.4F,10.8F, 42, () -> Ingredient.of(ModItems.DRAGON_IRON_INGOT));
}
