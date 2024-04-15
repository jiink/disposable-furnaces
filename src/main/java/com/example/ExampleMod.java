package com.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.util.Identifier;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final Block WOODEN_FURNACE = new Block(Block.Settings.create().strength(1.0f));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		Registry.register(Registries.BLOCK, new Identifier("smelting-in-a-pinch", "wooden_furnace"), WOODEN_FURNACE);
		Registry.register(Registries.ITEM, new Identifier("smelting-in-a-pinch", "wooden_furnace"), new BlockItem(WOODEN_FURNACE, new Item.Settings()));
	}
}