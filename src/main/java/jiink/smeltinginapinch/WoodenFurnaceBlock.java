package jiink.smeltinginapinch;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
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

import java.util.function.BiConsumer;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.AbstractBlock;

public class WoodenFurnaceBlock extends BlockWithEntity {
    public WoodenFurnaceBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    // @Nullable
    // public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    //     return world.isClient ? null : checkType(type, SmeltingInAPinch.WOODEN_FURNACE_BLOCK_ENTITY, WoodenFurnaceBlockEntity::tick);
    // }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenFurnaceBlockEntity(pos, state);
    }

    public static final MapCodec<WoodenFurnaceBlock> CODEC = WoodenFurnaceBlock.createCodec(WoodenFurnaceBlock::new);
    @Override
    protected MapCodec<WoodenFurnaceBlock> getCodec() {
        return CODEC;
    }
}