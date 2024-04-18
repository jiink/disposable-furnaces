package jiink.smeltinginapinch;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class SmeltingInAPinchClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		// Make sure screen handler and screen are linked together
		HandledScreens.register(SmeltingInAPinch.WOODEN_FURNACE_SCREEN_HANDLER, WoodenFurnaceScreen::new);
	}
}