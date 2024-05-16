package jiink.smeltinginapinch;

import jiink.smeltinginapinch.config.MyConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class GunpowderFurnaceBlockEntity extends DisposableFurnaceBlockEntity {
    public GunpowderFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state, SmeltingInAPinch.GUNPOWDER_FURNACE_BLOCK_ENTITY);
    }

    @Override
    protected void burnoutDestroy(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
        // explode
        if (MyConfig.HANDLER.instance().furnaceGriefing) {
            world.createExplosion(null, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 4.0f, false, World.ExplosionSourceType.BLOCK);
        } else {
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        }
    }
}
