package net.lomeli.craftcards.gui.slot;

import net.lomeli.craftcards.items.ItemCraftCards;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCardBag extends Slot {

    public SlotCardBag(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        if(itemStack.getItem() instanceof ItemCraftCards)
            return true;
        else
            return false;
    }
}
