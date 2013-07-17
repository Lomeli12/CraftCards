package net.lomeli.craftcards.items;

import net.lomeli.craftcards.CraftCards;
import net.lomeli.craftcards.container.ContainerCardBag;
import net.lomeli.craftcards.lib.ModInts;
import net.lomeli.craftcards.lib.ModStrings;

import net.lomeli.lomlib.item.ItemGeneric;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCardBag extends ItemGeneric
{
	public ItemCardBag(int id)
    {
	    super(id, ModStrings.MOD_ID.toLowerCase(), "cardbag");
	    this.setMaxStackSize(1);
	    this.setCreativeTab(CreativeTabs.tabTools);
    }
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, 
			int indexInInventory, boolean isCurrentItem)
	{
		if (world.isRemote || !isCurrentItem)
			return;
		
		if(ContainerCardBag.class.isAssignableFrom(((EntityPlayer)entity).openContainer.getClass()))
		{
			ContainerCardBag container = (ContainerCardBag)((EntityPlayer)entity).openContainer;
			if(container.updateNotification)
			{
				container.intentoryCards.writeToNBT(itemStack.stackTagCompound);
				container.updateNotification = false;
			}
		}
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world,
            EntityPlayer player)
    {
		if(!world.isRemote)
		{
			if(!player.isSneaking())
				player.openGui(CraftCards.instance, ModInts.bagGuiID, world, 
					(int)player.posX, (int)player.posY, (int)player.posZ);
		}
		
		return itemStack;
    }

}
