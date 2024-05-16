package jiink.smeltinginapinch;

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
        // explode
        world.createExplosion(null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 4.0f, true, World.ExplosionSourceType.BLOCK);
    }
}
