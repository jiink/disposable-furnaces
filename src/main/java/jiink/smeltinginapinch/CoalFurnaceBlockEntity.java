package jiink.smeltinginapinch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class CoalFurnaceBlockEntity extends DisposableFurnaceBlockEntity {
    public CoalFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 48, 10.0f, SmeltingInAPinch.COAL_FURNACE_BLOCK_ENTITY);
    }

    @Override
    protected void burnoutDestroy(World world, BlockPos pos, BlockState state) {
        // explode
        world.createExplosion(null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 4.0f, true, World.ExplosionSourceType.BLOCK);
        // make block breaking particles and sound
//        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
//        world.playSound(
//                null,
//                pos,
//                SoundEvents.BLOCK_SCAFFOLDING_BREAK,
//                SoundCategory.BLOCKS,
//                1f,
//                0.75f
//        );
//        // destroy block and replace it with fire
//        world.setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
//        world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
//        world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState());
    }
}
