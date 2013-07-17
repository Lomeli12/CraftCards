package net.lomeli.craftcards.container;

import net.lomeli.craftcards.gui.slot.SlotCard;
import net.lomeli.craftcards.gui.slot.SlotCardOutput;
import net.lomeli.craftcards.gui.slot.SlotFakeCraft;
import net.lomeli.craftcards.tileentity.TileEntityStamper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerStamper extends Container
{
	public TileEntityStamper tileEntity;
	public ContainerStamper(InventoryPlayer inventoryPlayer, TileEntityStamper tile)
	{
		this.tileEntity = tile;
		
		this.addSlotToContainer(new SlotFakeCraft(inventoryPlayer.player, this.tileEntity, this.tileEntity, 10, 112, 35));
		
		for(int l = 0; l < 3; l++)
        {
        	for(int k1 = 0; k1 < 3; k1++)
        	{
        		this.addSlotToContainer(new Slot(this.tileEntity, k1 + l * 3, 30 + k1 * 18, 17 + l * 18));
        	}
        }
		
		this.addSlotToContainer(new SlotCard(this.tileEntity, 9, 6, 35));
		
		this.addSlotToContainer(new SlotCardOutput(this.tileEntity, 11, 148, 35));
		
		for(int l = 0; l < 3; ++l)
        {
        	for(int i1 = 0; i1 < 9; ++i1)
        	{
        		this.addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
        	}
        }

        for(int j1 = 0; j1 < 9; j1++)
        {
        	this.addSlotToContainer(new Slot(inventoryPlayer, j1, 8 + j1 * 18, 142));
        }
		
        this.onCraftMatrixChanged(this.tileEntity);
	}
	
	@Override
    public void onCraftMatrixChanged(IInventory iinventory)
	{
		InventoryCrafting ic = new InventoryCrafting(new ContainerDummy(), 3, 3);
		for(int i = 0; i < 9; i++)
		{
			ic.setInventorySlotContents(i, this.tileEntity.getStackInSlot(i));
		}
		
		this.tileEntity.setInventorySlotContents(10, CraftingManager.getInstance().findMatchingRecipe(ic, this.tileEntity.worldObj));
	}

	@Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
	    return true;
    }
	
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 0)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (par2 >= 10 && par2 < 37)
            {
                if (!this.mergeItemStack(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (par2 >= 37 && par2 < 46)
            {
                if (!this.mergeItemStack(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

}
