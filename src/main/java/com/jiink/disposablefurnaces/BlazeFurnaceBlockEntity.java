package com.jiink.disposablefurnaces;

import com.jiink.disposablefurnaces.config.MyConfig;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

public class BlazeFurnaceBlockEntity extends DisposableFurnaceBlockEntity {
    public BlazeFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, DisposableFurnaces.BLAZE_FURNACE_BLOCK_ENTITY);
    }

    @Override
    protected void burnoutDestroy(World world, BlockPos pos, BlockState state) {
        world.playSound(
                null,
                pos,
                SoundEvents.ITEM_FIRECHARGE_USE,
                SoundCategory.BLOCKS,
                1f,
                1f
        );
        world.setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
        // explode
        if (MyConfig.HANDLER.instance().furnaceGriefing) {
            world.createExplosion(null, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 1.0f, true, World.ExplosionSourceType.MOB);
        } else {
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        }
    }

    @Override
    protected void onRandomTick(World world, BlockPos pos, BlockState state, Random random) {
        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            return;
        }
        int i = random.nextInt(3);
        if (i > 0) {
            BlockPos blockPos = pos;
            for (int j = 0; j < i; ++j) {
                if (!world.canSetBlock(blockPos = blockPos.add(random.nextInt(3) - 1, 1, random.nextInt(3) - 1))) {
                    return;
                }
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isAir()) {
                    if (!this.canLightFire(world, blockPos)) continue;
                    world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                    return;
                }
                if (!blockState.blocksMovement()) continue;
                return;
            }
        } else {
            for (int k = 0; k < 3; ++k) {
                BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (!world.canSetBlock(blockPos2)) {
                    return;
                }
                if (!world.isAir(blockPos2.up()) || !this.hasBurnableBlock(world, blockPos2)) continue;
                world.setBlockState(blockPos2.up(), AbstractFireBlock.getState(world, blockPos2));
            }
        }
    }

    private boolean canLightFire(WorldView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (!this.hasBurnableBlock(world, pos.offset(direction))) continue;
            return true;
        }
        return false;
    }

    private boolean hasBurnableBlock(WorldView world, BlockPos pos) {
        if (pos.getY() >= world.getBottomY() && pos.getY() < world.getTopY() && !world.isChunkLoaded(pos)) {
            return false;
        }
        return world.getBlockState(pos).isBurnable();
    }
}
