package com.example;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.AbstractBlock;

public class WoodenFurnaceBlock extends AbstractFurnaceBlock {
    public WoodenFurnaceBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenFurnaceBlockEntity(pos, state);
    }

    public static final MapCodec<WoodenFurnaceBlock> CODEC = WoodenFurnaceBlock.createCodec(WoodenFurnaceBlock::new);
    @Override
    protected MapCodec<WoodenFurnaceBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof WoodenFurnaceBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory)((Object)blockEntity));
        }
    }
}