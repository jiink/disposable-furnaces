package jiink.smeltinginapinch;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DemoBlock extends BlockWithEntity {
    public DemoBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DemoBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && hand == Hand.MAIN_HAND) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof DemoBlockEntity) {
                DemoBlockEntity demoBlockEntity = (DemoBlockEntity)blockEntity;
                demoBlockEntity.number++;
                demoBlockEntity.markDirty();
                player.sendMessage(Text.literal("Number is " + demoBlockEntity.number), false);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    public static final MapCodec<DemoBlock> CODEC = DemoBlock.createCodec(DemoBlock::new);
    @Override
    protected MapCodec<DemoBlock> getCodec() {
        return CODEC;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, SmeltingInAPinch.DEMO_BLOCK_ENTITY, (world1, pos, state1, be) -> DemoBlockEntity.tick(world1, pos, state1, be));
    }
}
