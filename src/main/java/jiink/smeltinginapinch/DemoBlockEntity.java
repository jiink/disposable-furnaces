package jiink.smeltinginapinch;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class DemoBlockEntity extends BlockEntity {
    
    public int number = 7;
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
}
