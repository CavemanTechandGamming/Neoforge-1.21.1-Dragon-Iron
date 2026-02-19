package com.caveman.cavemansdragoniron.block;

import com.caveman.cavemansdragoniron.block.entity.DragonIronFurnaceBlockEntity;
import com.caveman.cavemansdragoniron.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

public class DragonIronFurnaceBlock extends AbstractFurnaceBlock {

    public static final MapCodec<DragonIronFurnaceBlock> CODEC = RecordCodecBuilder.mapCodec(inst ->
            inst.group(BlockBehaviour.Properties.CODEC.fieldOf("properties").forGetter(DragonIronFurnaceBlock::getProperties))
                    .apply(inst, DragonIronFurnaceBlock::new));

    public DragonIronFurnaceBlock(Properties properties) {
        super(properties);
    }

    private BlockBehaviour.Properties getProperties() {
        return this.properties;
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite())
                .setValue(BlockStateProperties.LIT, false);
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DragonIronFurnaceBlockEntity furnace) {
            player.openMenu(furnace, pos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DragonIronFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlockEntities.DRAGON_IRON_FURNACE.get(), DragonIronFurnaceBlockEntity::serverTick);
    }
}
