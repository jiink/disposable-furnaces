// package jiink.smeltinginapinch;

// import org.jetbrains.annotations.Nullable;

// import com.mojang.serialization.MapCodec;

// import net.minecraft.block.AbstractBlock.Settings;
// import net.minecraft.block.AbstractFurnaceBlock;
// import net.minecraft.block.BlockState;
// import net.minecraft.block.entity.BlockEntity;
// import net.minecraft.entity.player.PlayerEntity;
// import net.minecraft.screen.NamedScreenHandlerFactory;
// import net.minecraft.stat.Stats;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.world.BlockView;
// import net.minecraft.world.World;

// public class TestFurnaceBlock extends AbstractFurnaceBlock {
//     public TestFurnaceBlock(Settings settings) {
//         super(settings);
//     }
 
//     @Override
//     public @Nullable BlockEntity createBlockEntity(BlockView world) {
//         return new TestFurnaceBlockEntity();
//     }
 
//     @Override
//     public void openScreen(World world, BlockPos pos, PlayerEntity player) {
//         //This is called by the onUse method inside AbstractFurnaceBlock so
//         //it is a little bit different of how you open the screen for normal container
//         BlockEntity blockEntity = world.getBlockEntity(pos);
//         if (blockEntity instanceof TestFurnaceBlockEntity) {
//             player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
//             // Optional: increment player's stat
//             player.incrementStat(Stats.INTERACT_WITH_FURNACE);
//         }
//     }

//     public static final MapCodec<TestFurnaceBlock> CODEC = TestFurnaceBlock.createCodec(TestFurnaceBlock::new);
//     @Override
//     protected MapCodec<TestFurnaceBlock> getCodec() {
//         return CODEC;
//     }

//     @Override
//     public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'createBlockEntity'");
//     }
// }