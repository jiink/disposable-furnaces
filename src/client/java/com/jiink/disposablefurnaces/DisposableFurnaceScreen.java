package com.jiink.disposablefurnaces;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DisposableFurnaceScreen extends HandledScreen<DisposableFurnaceScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(DisposableFurnaces.MOD_ID, "textures/gui/disposable_furnace_gui.png");

    public DisposableFurnaceScreen(DisposableFurnaceScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
//        titleY = 1000;
//        playerInventoryTitleY = 1000;
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        renderFlame(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 79, y + 35, 200, 0, handler.getScaledProgress(), 16);
        }
    }

    private void renderFlame(DrawContext context, int x, int y) {
        int flameHeight = handler.getScaledFuel();
        context.drawTexture(TEXTURE, x + 56, y + 54 + (14 - flameHeight), 200, 20 + (14 - flameHeight), 14, flameHeight);
        if (flameHeight <= 0) // Furnace is probably destroyed or right about to be destroyed
        {
            close();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
    
}
