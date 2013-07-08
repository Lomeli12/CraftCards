package net.lomeli.craftcards.gui.slot;

import net.lomeli.craftcards.items.ItemCraftCards;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;

public class SlotFakeCraft extends SlotCrafting
{

	public SlotFakeCraft(EntityPlayer par1EntityPlayer,
            IInventory par2iInventory, IInventory par3iInventory, int par4,
            int par5, int par6)
    {
	    super(par1EntityPlayer, par2iInventory, par3iInventory, par4, par5, par6);
    }
	
	@Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
		if(this.inventory.getStackInSlot(9) != null && 
			(this.inventory.getStackInSlot(9).getItem() instanceof ItemCraftCards))
			return false;
		else
			return true;
    }

}
