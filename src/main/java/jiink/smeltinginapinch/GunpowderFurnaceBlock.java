package jiink.smeltinginapinch;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GunpowderFurnaceBlock extends DisposableFurnaceBlock {

    public GunpowderFurnaceBlock(Settings settings) {
        super(settings);
    }

    public static final MapCodec<WoodenFurnaceBlock> CODEC = WoodenFurnaceBlock.createCodec(WoodenFurnaceBlock::new);
    @Override
    protected MapCodec<WoodenFurnaceBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GunpowderFurnaceBlockEntity(pos, state);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY();
            double z = (double) pos.getZ() + 0.5D;

            if (random.nextDouble() < 0.3D) {
                world.playSound(x, y, z, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            for (int i = 0; i < 4; i++) {
                Direction direction_1 = state.get(FACING);
                Direction.Axis direction$Axis_1 = direction_1.getAxis();

                double double_5 = random.nextDouble() * 0.6D - 0.3D;
                double double_6 = direction$Axis_1 == Direction.Axis.X ? (double) direction_1.getOffsetX() * 0.52D : double_5;
                double double_7 = random.nextDouble() * 6.0D / 16.0D;
                double double_8 = direction$Axis_1 == Direction.Axis.Z ? (double) direction_1.getOffsetZ() * 0.52D : double_5;

                world.addParticle(ParticleTypes.WHITE_SMOKE, x + double_6, y + double_7, z + double_8, double_6 * 0.2D, 0.2D, double_8 * 0.2D);
                world.addParticle(ParticleTypes.FIREWORK, x + double_6, y + double_7, z + double_8, double_6 * 0.2D, 0.2D, double_8 * 0.2D);
                world.addParticle(ParticleTypes.SWEEP_ATTACK, x + double_6, y + double_7, z + double_8, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, SmeltingInAPinch.GUNPOWDER_FURNACE_BLOCK_ENTITY, (world1, pos, state1, be) -> ((DisposableFurnaceBlockEntity) be).tick(world1, pos, state1));
    }
}
