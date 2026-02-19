package com.caveman.cavemansdragoniron.loot;

import com.caveman.cavemansdragoniron.Config;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

/**
 * Global loot modifier that adds an item with a chance read from config (base chance)
 * and increased by Looting enchantment (+10% per level, capped at 100%).
 * Used for dragon iron nugget from Endermen.
 */
public class AddItemWithConfigChanceModifier extends LootModifier {
    private static final int LOOTING_BONUS_PERCENT_PER_LEVEL = 10;

    public static final MapCodec<AddItemWithConfigChanceModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)).apply(inst, AddItemWithConfigChanceModifier::new));

    private final Item item;

    public AddItemWithConfigChanceModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(lootContext)) {
                return generatedLoot;
            }
        }

        int baseChancePercent = Config.DRAGON_IRON_NUGGET_ENDERMAN_DROP_CHANCE_PERCENT.get();
        ItemStack tool = lootContext.getParamOrNull(LootContextParams.TOOL);
        int lootingLevel = (tool != null && !tool.isEmpty())
                ? EnchantmentHelper.getItemEnchantmentLevel(lootContext.getLevel().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.LOOTING), tool)
                : 0;

        int effectiveChancePercent = Math.min(100, baseChancePercent + lootingLevel * LOOTING_BONUS_PERCENT_PER_LEVEL);
        if (effectiveChancePercent <= 0) {
            return generatedLoot;
        }
        if (effectiveChancePercent >= 100) {
            generatedLoot.add(new ItemStack(this.item));
            return generatedLoot;
        }

        if (lootContext.getRandom().nextInt(100) < effectiveChancePercent) {
            generatedLoot.add(new ItemStack(this.item));
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
