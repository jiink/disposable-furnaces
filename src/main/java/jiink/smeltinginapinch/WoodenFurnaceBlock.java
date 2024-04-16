package jiink.smeltinginapinch;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

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

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, SmeltingInAPinch.WOODEN_FURNACE_BLOCK_ENTITY, (world1, pos, state1, be) -> WoodenFurnaceBlockEntity.tick(world1, pos, state1, be));
    }

    public static <T extends BlockEntity> BlockEntityTicker<T> checkType(BlockEntityType<T> givenType, BlockEntityType<WoodenFurnaceBlockEntity> expectedType, BlockEntityTicker<? super WoodenFurnaceBlockEntity> ticker) {
        return givenType == expectedType ? (BlockEntityTicker<T>) ticker : null;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT).booleanValue()) {
            return;
        }
        double d = (double)pos.getX() + 0.5;
        double e = pos.getY();
        double f = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, e, f, SoundEvents.BLOCK_SMOKER_SMOKE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
        }
        world.addParticle(ParticleTypes.SMOKE, d, e + 1.1, f, 0.0, 0.0, 0.0);
    }
}