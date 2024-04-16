package jiink.smeltinginapinch;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.util.Identifier;



public class SmeltingInAPinch implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final WoodenFurnaceBlock WOODEN_FURNACE_BLOCK = Registry.register(
		Registries.BLOCK,
		new Identifier("smeltinginapinch", "wooden_furnace"),
		new WoodenFurnaceBlock(Block.Settings.create().strength(1.0f))
	);
	public static final Item WOODEN_FURANCE_BLOCK_ITEM = Registry.register(
		Registries.ITEM,
		new Identifier("smeltinginapinch", "wooden_furnace"),
		new BlockItem(WOODEN_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<WoodenFurnaceBlockEntity> WOODEN_FURNACE_BLOCK_ENTITY = Registry.register(
		Registries.BLOCK_ENTITY_TYPE,
		new Identifier("smeltinginapinch", "wooden_furnace_block_entity"),
		FabricBlockEntityTypeBuilder.create(WoodenFurnaceBlockEntity::new, WOODEN_FURNACE_BLOCK).build()
	);
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
			content.addAfter(Items.BLAST_FURNACE, WOODEN_FURANCE_BLOCK_ITEM);
		});
	}
}