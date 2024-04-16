package jiink.smeltinginapinch;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DemoBlockEntity extends BlockEntity {
    
    public int number = 0;
    private static final String NBT_ATTACHMENT_KEY = "number";
    
    public DemoBlockEntity(BlockPos pos, BlockState state) {
        super(SmeltingInAPinch.DEMO_BLOCK_ENTITY, pos, state);
        SmeltingInAPinch.LOGGER.info("I am a new demoblockentity!");
    }

    // Call markDirty() whenever changing the saved variable so this gets called.
    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt(NBT_ATTACHMENT_KEY, number);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        number = nbt.getInt(NBT_ATTACHMENT_KEY);
    }

    public static void tick(World world, BlockPos pos, BlockState state, DemoBlockEntity be) {
        if (!world.isClient) {
            //SmeltingInAPinch.LOGGER.info("Tick! " + be.number);

        }
    }
}
