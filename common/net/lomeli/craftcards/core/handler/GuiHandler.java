package net.lomeli.craftcards.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;

import net.lomeli.craftcards.container.ContainerCardBag;
import net.lomeli.craftcards.container.ContainerStamper;
import net.lomeli.craftcards.gui.GuiCardBag;
import net.lomeli.craftcards.gui.GuiStamper;
import net.lomeli.craftcards.inventory.InventoryCards;
import net.lomeli.craftcards.lib.ModInts;
import net.lomeli.craftcards.tileentity.TileEntityStamper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile instanceof TileEntityStamper)
            return new ContainerStamper(player.inventory,
                    (TileEntityStamper) tile);
        else if(ID == ModInts.bagGuiID) {
            InventoryCards newInv = new InventoryCards(
                    player.inventory.getCurrentItem(), player, 9);
            return new ContainerCardBag(newInv, player.inventory);
        }else
            return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile instanceof TileEntityStamper)
            return new GuiStamper(player.inventory, (TileEntityStamper) tile);
        else if(ID == ModInts.bagGuiID) {
            InventoryCards newInv = new InventoryCards(
                    player.inventory.getCurrentItem(), player, 9);
            return new GuiCardBag(newInv, player.inventory);
        }else
            return null;
    }

}
