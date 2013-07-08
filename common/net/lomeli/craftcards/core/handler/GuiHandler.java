package net.lomeli.craftcards.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;

import net.lomeli.craftcards.gui.GuiStamper;
import net.lomeli.craftcards.inventory.ContainerStamper;
import net.lomeli.craftcards.tileentity.TileEntityStamper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{

	@Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileEntityStamper)
			return new ContainerStamper(player.inventory, (TileEntityStamper)tile);
		else
			return null;
    }

	@Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileEntityStamper)
			return new GuiStamper(player.inventory, (TileEntityStamper)tile);
		else
			return null;
    }

}
