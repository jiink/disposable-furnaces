package jiink.smeltinginapinch;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class WoodenFurnaceScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final WoodenFurnaceBlockEntity blockEntity;

    public WoodenFurnaceScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
        new ArrayPropertyDelegate(5));
    }

    public WoodenFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity,
        PropertyDelegate arrayPropertyDelegate) {
        super(SmeltingInAPinch.WOODEN_FURNACE_SCREEN_HANDLER, syncId);
        checkSize(((Inventory) blockEntity), 2);
        this.inventory = ((Inventory) blockEntity);
        playerInventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = (WoodenFurnaceBlockEntity) blockEntity;
        
        this.addSlot(new Slot(inventory, 0, 56, 35));
        this.addSlot(new Slot(inventory, 1, 116, 35));
        this.addPlayerInventory(playerInventory);
        addProperties(arrayPropertyDelegate);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory inventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    public boolean isCrafting() {
        return this.propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 24;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledFuel() {
        int fuel = this.propertyDelegate.get(2);
        int maxFuel = this.propertyDelegate.get(4);
        int flameSize = 14;
        return maxFuel != 0 && fuel != 0 ? fuel * flameSize / maxFuel : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }
    
}
