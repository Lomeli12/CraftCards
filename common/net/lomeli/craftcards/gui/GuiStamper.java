package net.lomeli.craftcards.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.craftcards.container.ContainerStamper;
import net.lomeli.craftcards.lib.ModStrings;
import net.lomeli.craftcards.tileentity.TileEntityStamper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiStamper extends GuiContainer {
    public GuiStamper(InventoryPlayer inventoryPlayer, TileEntityStamper tile) {
        super(new ContainerStamper(inventoryPlayer, tile));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String name = "Card Stamper";
        int xStart = xSize / 2;
        fontRenderer.drawString(name,
                xStart - fontRenderer.getStringWidth(name) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation(ModStrings.MOD_ID
                .toLowerCase(), ModStrings.GUI_LOC + "stamperUI.png"));
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);
    }

}
