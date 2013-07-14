package net.lomeli.craftcards.gui.slot;

import net.lomeli.craftcards.items.ItemCraftCards;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCardOutput extends Slot
{

	public SlotCardOutput(IInventory par1iInventory, int par2, int par3, int par4)
    {
	    super(par1iInventory, par2, par3, par4);
    }
	
	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
		if(this.inventory.getStackInSlot(9) != null)
				this.inventory.setInventorySlotContents(9, null);
				
        this.onSlotChanged();
    }

	@Override
    public boolean isItemValid(ItemStack itemStack)
	{
		if(itemStack.getItem() instanceof ItemCraftCards && itemStack.getItemDamage() == 1)
			return true;
		else
			return false;
	}
}
