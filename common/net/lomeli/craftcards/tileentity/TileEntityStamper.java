package net.lomeli.craftcards.tileentity;

import net.lomeli.craftcards.container.ContainerDummy;
import net.lomeli.craftcards.items.ModItems;
import net.lomeli.craftcards.items.ItemCraftCards;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityStamper extends TileEntity 
	implements IInventory
{
	public ItemStack[] inventory;
	
	public TileEntityStamper()
	{
		inventory = new ItemStack[12];
	}

	@Override
    public void updateEntity()
    {
		super.updateEntity();
		
		InventoryCrafting ic = new InventoryCrafting(new ContainerDummy(), 3, 3);
		for(int i = 0; i < 9; i++)
		{
			ic.setInventorySlotContents(i, this.getStackInSlot(i));
		}
		
		this.setInventorySlotContents(10, CraftingManager.getInstance().findMatchingRecipe(ic, this.worldObj));
		
		if(this.getStackInSlot(10) != null && 
			(this.getStackInSlot(9) != null && this.getStackInSlot(9).getItem() instanceof ItemCraftCards))
		{
			ItemStack outputCard = new ItemStack(ModItems.craftCard, 1, 1);
			ItemCraftCards.setOutput(outputCard, this.getStackInSlot(10));
			ItemCraftCards.encode(outputCard);
			for(int i = 0; i < 9; i++)
			{
				if(this.getStackInSlot(i) != null)
				{
					ItemCraftCards.setInputs(outputCard, this.getStackInSlot(i), i);
				}
			}
			this.setInventorySlotContents(11, outputCard);	
		}
		else
			this.setInventorySlot(11, null);
    }
	
	@Override
    public int getSizeInventory()
    {
        return inventory.length;
    }
    
    public void setItemStack(int slot, ItemStack item)
    {
    	inventory[slot] = item;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null)
        {
        	if(itemStack.stackSize <= amount)
        		setInventorySlotContents(slot, null);
        	else
        	{
        		itemStack.splitStack(amount);
        		if (itemStack.stackSize == 0)
        			setInventorySlotContents(slot, null);
        	}
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null)
        {
            setInventorySlotContents(slot, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        inventory[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }
    
    
    public void setInventorySlot(int slot, ItemStack itemStack)
    {
    	inventory[slot] = itemStack;
    }
    
	@Override
    public String getInvName()
    {
	    return null;
    }

	@Override
    public boolean isInvNameLocalized()
    {
	    return false;
    }

	@Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
                        zCoord + 0.5) < 64;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return true;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        loadNBT(nbtTagCompound);
    }
    
    public void loadNBT(NBTTagCompound nbtTagCompound)
    {
    	NBTTagList tagList = nbtTagCompound.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inventory.length)
            {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
            }

        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        addToNBT(nbtTagCompound);
    }
    
    public void addToNBT(NBTTagCompound nbtTagCompound)
    {
    	NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);

                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag("Inventory", tagList);
    }
    
    @Override
    public Packet getDescriptionPacket() 
    {
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound tag = packet != null ? packet.customParam1 : new NBTTagCompound();

        addToNBT(tag);

        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) 
    {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt.customParam1;
        loadNBT(tag);
    }

}
