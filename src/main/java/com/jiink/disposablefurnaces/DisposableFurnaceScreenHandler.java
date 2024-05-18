package com.jiink.disposablefurnaces;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class DisposableFurnaceScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final DisposableFurnaceBlockEntity blockEntity;
    protected final World world;
    private final RecipeType<SmeltingRecipe> recipeType = RecipeType.SMELTING;

    public DisposableFurnaceScreenHandler(int syncId, PlayerInventory inventory, DisposableFurnacePayload payload) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(payload.blockPos()), new ArrayPropertyDelegate(5));
    }

    public DisposableFurnaceScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity,
                                          PropertyDelegate arrayPropertyDelegate) {
        super(DisposableFurnaces.DISPOSABLE_FURNACE_SCREEN_HANDLER, syncId);
        checkSize(((Inventory) blockEntity), 2);
        this.inventory = ((Inventory) blockEntity);
        playerInventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = (DisposableFurnaceBlockEntity) blockEntity;
        this.world = playerInventory.player.getWorld();
        
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

    protected boolean isSmeltable(ItemStack itemStack) {
        return this.world.getRecipeManager().getFirstMatch(this.recipeType, new SimpleInventory(itemStack), this.world).isPresent();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = slots.get(invSlot); // the slot we shift clicked on
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack(); // the item stack we shift clicked on
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) { // did we shift click on one of the furnace's slots?
                if (!this.insertItem(originalStack, this.inventory.size(), slots.size(), true)) { // try to move the item stack to the player's inventory (reverse since fromLast=true)
                    return ItemStack.EMPTY;
                }
            } else if (this.isSmeltable(newStack.copy())) { // did we shift click on an item that can be smelted?
                if (!this.insertItem(originalStack, 0, 1, false)) { // try to move the item stack into the furnace (just the input slot)
                    return ItemStack.EMPTY; // couldn't do it, no items transferred
                }
            } else {
                return ItemStack.EMPTY; // couldn't do it, no items transferred
            }

            if (originalStack.isEmpty()) { // all the items were moved out
                slot.setStack(ItemStack.EMPTY); // so make sure the slot we clicked on is empty
            } else {
                slot.markDirty(); // not everything was moved out, so mark it dirty. ends up making this function run again.
            }
        }
        return newStack;
    }
    
}
