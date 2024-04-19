package jiink.smeltinginapinch;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;


public class SmeltingInAPinch implements ModInitializer {
	
	public static final String MOD_ID = "smeltinginapinch";
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Block DEMO_BLOCK = Registry.register(
		Registries.BLOCK,
		new Identifier(MOD_ID, "demo_block"),
		new DemoBlock(FabricBlockSettings.create().strength(1.0f))
	);
	public static final Item DEMO_BLOCK_ITEM = Registry.register(
		Registries.ITEM,
		new Identifier(MOD_ID, "demo_block"),
		new BlockItem(DEMO_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<DemoBlockEntity> DEMO_BLOCK_ENTITY = Registry.register(
		Registries.BLOCK_ENTITY_TYPE, 
		new Identifier(MOD_ID, "demo_block_entity"),
		FabricBlockEntityTypeBuilder.create(DemoBlockEntity::new, DEMO_BLOCK).build()
	);
	// Wooden furnace stuff
	public static final Block WOODEN_FURNACE_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "wooden_furnace"),
			new WoodenFurnaceBlock(FabricBlockSettings.create().strength(2.5f).sounds(BlockSoundGroup.WOOD).burnable().luminance(blockstateToLuminance()))
	);
	public static final Item WOODEN_FURANCE_BLOCK_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(MOD_ID, "wooden_furnace"),
			new BlockItem(WOODEN_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<WoodenFurnaceBlockEntity> WOODEN_FURNACE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "wooden_furnace_block_entity"),
			FabricBlockEntityTypeBuilder.create(WoodenFurnaceBlockEntity::new, WOODEN_FURNACE_BLOCK).build()
	);

	// Dried kelp furnace stuff
	public static final Block DRIED_KELP_FURNACE_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "dried_kelp_furnace"),
			new DriedKelpFurnaceBlock(FabricBlockSettings.create().strength(5.0f).sounds(BlockSoundGroup.STONE).burnable().luminance(blockstateToLuminance()))
	);
	public static final Item DRIED_KELP_FURANCE_BLOCK_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(MOD_ID, "dried_kelp_furnace"),
			new BlockItem(DRIED_KELP_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<DriedKelpFurnaceBlockEntity> DRIED_KELP_FURNACE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "dried_kelp_furnace_block_entity"),
			FabricBlockEntityTypeBuilder.create(DriedKelpFurnaceBlockEntity::new, DRIED_KELP_FURNACE_BLOCK).build()
	);

	// Coal furnace stuff
	public static final Block COAL_FURNACE_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "coal_furnace"),
			new CoalFurnaceBlock(FabricBlockSettings.create().strength(5.0f).sounds(BlockSoundGroup.STONE).burnable().luminance(blockstateToLuminance()))
	);
	public static final Item COAL_FURANCE_BLOCK_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(MOD_ID, "coal_furnace"),
			new BlockItem(COAL_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<CoalFurnaceBlockEntity> COAL_FURNACE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "coal_furnace_block_entity"),
			FabricBlockEntityTypeBuilder.create(CoalFurnaceBlockEntity::new, COAL_FURNACE_BLOCK).build()
	);

	// Charcoal furnace stuff
	public static final Block CHARCOAL_FURNACE_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "charcoal_furnace"),
			new CharcoalFurnaceBlock(FabricBlockSettings.create().strength(5.0f).sounds(BlockSoundGroup.STONE).burnable().luminance(blockstateToLuminance()))
	);
	public static final Item CHARCOAL_FURANCE_BLOCK_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(MOD_ID, "charcoal_furnace"),
			new BlockItem(CHARCOAL_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<CharcoalFurnaceBlockEntity> CHARCOAL_FURNACE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "charcoal_furnace_block_entity"),
			FabricBlockEntityTypeBuilder.create(CharcoalFurnaceBlockEntity::new, CHARCOAL_FURNACE_BLOCK).build()
	);

	// Blaze furnace stuff
	public static final Block BLAZE_FURNACE_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "blaze_furnace"),
			new BlazeFurnaceBlock(FabricBlockSettings.create().strength(5.0f).sounds(BlockSoundGroup.STONE).burnable().luminance(blockstateToLuminance()))
	);
	public static final Item BLAZE_FURANCE_BLOCK_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(MOD_ID, "blaze_furnace"),
			new BlockItem(BLAZE_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<BlazeFurnaceBlockEntity> BLAZE_FURNACE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "blaze_furnace_block_entity"),
			FabricBlockEntityTypeBuilder.create(BlazeFurnaceBlockEntity::new, BLAZE_FURNACE_BLOCK).build()
	);

	// Lava furnace stuff
	public static final Block LAVA_FURNACE_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "lava_furnace"),
			new LavaFurnaceBlock(FabricBlockSettings.create().strength(5.0f).sounds(BlockSoundGroup.STONE).burnable().luminance(blockstateToLuminance()))
	);
	public static final Item LAVA_FURANCE_BLOCK_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(MOD_ID, "lava_furnace"),
			new BlockItem(LAVA_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<LavaFurnaceBlockEntity> LAVA_FURNACE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "lava_furnace_block_entity"),
			FabricBlockEntityTypeBuilder.create(LavaFurnaceBlockEntity::new, LAVA_FURNACE_BLOCK).build()
	);

	// Gunpowder furnace stuff
	public static final Block GUNPOWDER_FURNACE_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "gunpowder_furnace"),
			new GunpowderFurnaceBlock(FabricBlockSettings.create().strength(5.0f).sounds(BlockSoundGroup.STONE).burnable().luminance(blockstateToLuminance()))
	);
	public static final Item GUNPOWDER_FURANCE_BLOCK_ITEM = Registry.register(
			Registries.ITEM,
			new Identifier(MOD_ID, "gunpowder_furnace"),
			new BlockItem(GUNPOWDER_FURNACE_BLOCK, new Item.Settings())
	);
	public static final BlockEntityType<GunpowderFurnaceBlockEntity> GUNPOWDER_FURNACE_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "gunpowder_furnace_block_entity"),
			FabricBlockEntityTypeBuilder.create(GunpowderFurnaceBlockEntity::new, GUNPOWDER_FURNACE_BLOCK).build()
	);

	public static final ScreenHandlerType<DisposableFurnaceScreenHandler> DISPOSABLE_FURNACE_SCREEN_HANDLER;
	static	{
		DISPOSABLE_FURNACE_SCREEN_HANDLER = Registry.register(
				Registries.SCREEN_HANDLER,
				new Identifier(MOD_ID, "disposable_furnace_screen_handler"),
				new ExtendedScreenHandlerType<>(DisposableFurnaceScreenHandler::new)
		);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Getting ready to smelt!");
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
			content.addAfter(Items.BLAST_FURNACE, GUNPOWDER_FURANCE_BLOCK_ITEM);
			content.addAfter(Items.BLAST_FURNACE, LAVA_FURANCE_BLOCK_ITEM);
			content.addAfter(Items.BLAST_FURNACE, BLAZE_FURANCE_BLOCK_ITEM);
			content.addAfter(Items.BLAST_FURNACE, CHARCOAL_FURANCE_BLOCK_ITEM);
			content.addAfter(Items.BLAST_FURNACE, COAL_FURANCE_BLOCK_ITEM);
			content.addAfter(Items.BLAST_FURNACE, DRIED_KELP_FURANCE_BLOCK_ITEM);
			content.addAfter(Items.BLAST_FURNACE, WOODEN_FURANCE_BLOCK_ITEM);
			content.addAfter(Items.TORCH, DEMO_BLOCK_ITEM);
		});

		FlammableBlockRegistry.getDefaultInstance().add(WOODEN_FURNACE_BLOCK, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(DRIED_KELP_FURNACE_BLOCK, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(COAL_FURNACE_BLOCK, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(CHARCOAL_FURNACE_BLOCK, 5, 5);
		// TODO: Make lava and blaze furnaces burn other stuff around it like real lava does.

		FuelRegistry.INSTANCE.add(WOODEN_FURANCE_BLOCK_ITEM, 300 * 8);
		FuelRegistry.INSTANCE.add(DRIED_KELP_FURANCE_BLOCK_ITEM, 1600 * 8); // TODO
		FuelRegistry.INSTANCE.add(COAL_FURANCE_BLOCK_ITEM, 1600 * 8);
		FuelRegistry.INSTANCE.add(CHARCOAL_FURANCE_BLOCK_ITEM, 1600 * 8);
		FuelRegistry.INSTANCE.add(LAVA_FURANCE_BLOCK_ITEM, 1600 * 8);
		FuelRegistry.INSTANCE.add(BLAZE_FURANCE_BLOCK_ITEM, 1600 * 8);
	}

	private static ToIntFunction<BlockState> blockstateToLuminance() {
		return (blockState) -> (Boolean)blockState.get(Properties.LIT) ? 13 : 0;
	}
}