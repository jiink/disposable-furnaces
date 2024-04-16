package jiink.smeltinginapinch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DemoBlock extends Block implements BlockEntityProvider {
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
}
