package jiink.smeltinginapinch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class CharcoalFurnaceBlockEntity extends DisposableFurnaceBlockEntity {
    public CharcoalFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, 48, 10.0F, SmeltingInAPinch.CHARCOAL_FURNACE_BLOCK_ENTITY);
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
    }
}
