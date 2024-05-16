package com.jiink.disposablefurnaces;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class DisposableFurnacesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// Make sure screen handler and screen are linked together
		HandledScreens.register(DisposableFurnaces.DISPOSABLE_FURNACE_SCREEN_HANDLER, DisposableFurnaceScreen::new);
	}
}