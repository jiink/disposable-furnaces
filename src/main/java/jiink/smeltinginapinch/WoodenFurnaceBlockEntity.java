package jiink.smeltinginapinch;

import java.util.Optional;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;

public class WoodenFurnaceBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private static int INPUT_SLOT = 0;
    private static int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 20;
    private int maxFuel = 100;
    private int fuelRemaining = maxFuel;
    private int fuelStarted = 0; // 0 or 1

    public WoodenFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(SmeltingInAPinch.WOODEN_FURNACE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WoodenFurnaceBlockEntity.this.progress;
                    case 1 -> WoodenFurnaceBlockEntity.this.maxProgress;
                    case 2 -> WoodenFurnaceBlockEntity.this.fuelRemaining;
                    case 3 -> WoodenFurnaceBlockEntity.this.fuelStarted;
                    case 4 -> WoodenFurnaceBlockEntity.this.maxFuel;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WoodenFurnaceBlockEntity.this.progress = value;
                    case 1 -> WoodenFurnaceBlockEntity.this.maxProgress = value;
                    case 2 -> WoodenFurnaceBlockEntity.this.fuelRemaining = value;
                    case 3 -> WoodenFurnaceBlockEntity.this.fuelStarted = value;
                    case 4 -> WoodenFurnaceBlockEntity.this.maxFuel = value;
                };
            }

            @Override
            public int size() {
                return 5;
            }
            
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Wooden Furnace");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WoodenFurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
    
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient) {
            return;
        }
        if (fuelStarted == 1) {
            fuelRemaining--;
        }
        if (fuelRemaining <= 0) {
            // explode
            //world.createExplosion(null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 4.0f, true, ExplosionSourceType.MOB);
            // destroy block and replace it with fire
            world.setBlockState(pos, net.minecraft.block.Blocks.FIRE.getDefaultState());
        }
        if (isOutputSlotEmptyOrReceivable()) {
            if (this.hasRecipe()) {
                if (fuelStarted == 0) {
                    fuelStarted = 1;
                }
                this.increaseCraftProgress();
                markDirty(world, pos, state);
                if (hasCraftingFinished()) {
                    this.craftItem();
                    this.resetProgress();
                }
            } else {
                this.resetProgress();
            }
        } else {
            this.resetProgress();
            markDirty(world, pos, state  );
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    protected boolean isSmeltable(ItemStack itemStack) {
        return this.world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(itemStack), this.world).isPresent();
    }

    private ItemStack getResultItem(ItemStack input) {
        Optional<RecipeEntry<SmeltingRecipe>> recipeEntry = this.world.getRecipeManager()
                                                    .getFirstMatch(RecipeType.SMELTING, new SimpleInventory(input), this.world);
        if (recipeEntry.isPresent()) {
            return recipeEntry.get().value().craft(new SimpleInventory(input), null);
        } else {
            return ItemStack.EMPTY;
        }
    }

    private void craftItem() {
        ItemStack removedStack = this.removeStack(INPUT_SLOT, 1);
        //ItemStack result = new ItemStack(Items.IRON_INGOT);
        ItemStack result = getResultItem(removedStack);
        this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), getStack(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasCraftingFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        boolean hasInput = isSmeltable(getStack(INPUT_SLOT));
        if (!hasInput) {
            return false;
        }
        ItemStack result = getResultItem(getStack(INPUT_SLOT));
        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }
    
}
