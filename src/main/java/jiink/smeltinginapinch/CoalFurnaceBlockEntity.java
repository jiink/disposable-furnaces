package jiink.smeltinginapinch;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;

public class CoalFurnaceBlockEntity extends DisposableFurnaceBlockEntity {
    public CoalFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 48, 10.0f, SmeltingInAPinch.COAL_FURNACE_BLOCK_ENTITY);
    }

    @Override
    protected void burnoutDestroy(World world, BlockPos pos, BlockState state) {
        // make block breaking particles and sound
        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        world.playSound(
                null,
                pos,
                SoundEvents.ITEM_FIRECHARGE_USE,
                SoundCategory.BLOCKS,
                1f,
                0.75f
        );
        // destroy block and replace it with fire
        world.setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
        // place fire around it
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                BlockPos pos2 = pos.add(i - 1, 0, j - 1);
                if (!world.canSetBlock(pos2)) {
                    return;
                }
                if (!world.isAir(pos2.up()) || !this.hasBurnableBlock(world, pos2)) continue;
                world.setBlockState(pos2.up(), AbstractFireBlock.getState(world, pos2));
            }
        }
    }

    private boolean hasBurnableBlock(WorldView world, BlockPos pos) {
        if (pos.getY() >= world.getBottomY() && pos.getY() < world.getTopY() && !world.isChunkLoaded(pos)) {
            return false;
        }
        return world.getBlockState(pos).isBurnable();
    }
}
