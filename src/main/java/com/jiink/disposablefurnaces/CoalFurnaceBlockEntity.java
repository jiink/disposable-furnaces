package com.jiink.disposablefurnaces;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

public class CoalFurnaceBlockEntity extends DisposableFurnaceBlockEntity {
    public CoalFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, DisposableFurnaces.COAL_FURNACE_BLOCK_ENTITY);
    }

    @Override
    protected void burnoutDestroy(World world, BlockPos pos, BlockState state) {
        // place fire around it
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                BlockPos pos2 = pos.add(i, 0, j);
                if (world.canSetBlock(pos2) && world.getBlockState(pos2).isAir()) {
                    world.setBlockState(pos2, AbstractFireBlock.getState(world, pos2));
                }
            }
        }
        // make block breaking particles and sound
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        world.playSound(
                null,
                pos,
                SoundEvents.ITEM_CROSSBOW_SHOOT,
                SoundCategory.BLOCKS,
                1f,
                0.75f
        );
        // destroy block and replace it with fire
        world.setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
    }

    private boolean hasBurnableBlock(WorldView world, BlockPos pos) {
        if (pos.getY() >= world.getBottomY() && pos.getY() < world.getTopY() && !world.isChunkLoaded(pos)) {
            return false;
        }
        return world.getBlockState(pos).isBurnable();
    }
}
