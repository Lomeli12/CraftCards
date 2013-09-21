package net.lomeli.craftcards.gui;

import org.lwjgl.opengl.GL11;

import net.lomeli.craftcards.container.ContainerCardBag;
import net.lomeli.craftcards.inventory.InventoryCards;
import net.lomeli.craftcards.lib.ModStrings;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCardBag extends GuiContainer {
    public GuiCardBag(InventoryCards bagInv, InventoryPlayer playerInv) {
        super(new ContainerCardBag(bagInv, playerInv));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation(ModStrings.MOD_ID
                .toLowerCase(), ModStrings.GUI_LOC + "cardbagGUI.png"));
        int var5 = (width - xSize) / 2;
        int var6 = (height - ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, xSize, ySize);
    }

}
