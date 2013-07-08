package net.lomeli.craftcards.items;

import java.util.ArrayList;
import java.util.List;

import net.lomeli.craftcards.lib.ModStrings;

import net.lomeli.lomlib.util.NBTUtil;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemCraftCards extends Item
{
	public Icon[] iconArray;

	public ItemCraftCards(int id)
    {
	    super(id);
	    this.setMaxStackSize(1);
	    this.setCreativeTab(CreativeTabs.tabTools);
    }
	
	public static void setInputs(ItemStack itemStack, ItemStack input, int inputNum)
	{
		NBTUtil.setInteger(itemStack, "inputID" + inputNum, input.itemID);
		NBTUtil.setInteger(itemStack, "inputMeta" + inputNum, input.getItemDamage());
	}
	
	public static ItemStack getInputs(ItemStack itemStack, int inputNum)
	{
		int id = NBTUtil.getInt(itemStack, "inputID" + inputNum);
		int meta = NBTUtil.getInt(itemStack, "inputMeta" + inputNum);
		if(id == 0)
			return null;
		return new ItemStack(id, 1, meta);
	}
	
	public static void setOutput(ItemStack itemStack, ItemStack output)
	{
		NBTUtil.setInteger(itemStack, "OutItemID", output.itemID);
		NBTUtil.setInteger(itemStack, "OutItemMeta", output.getItemDamage());
		NBTUtil.setInteger(itemStack, "OutItemSize", output.stackSize);
	}
	
	public static void encode(ItemStack itemStack)
	{
		NBTUtil.setBoolean(itemStack, "encoded", true);
	}
	
	public static boolean encoded(ItemStack itemStack)
	{
		return NBTUtil.getBoolean(itemStack, "encoded");
	}
	
	public static ItemStack getOuput(ItemStack itemStack)
	{
		int id = NBTUtil.getInt(itemStack, "OutItemID");
		int meta = NBTUtil.getInt(itemStack, "OutItemMeta");
		int size = NBTUtil.getInt(itemStack, "OutItemSize");
		return new ItemStack(id, size, meta);
	}
	
	@Override
    public void registerIcons(IconRegister iconRegister)
    {
		iconArray = new Icon[2];
		
		iconArray[0] = iconRegister.registerIcon(ModStrings.MOD_ID.toLowerCase() + ":newCard");
		iconArray[1] = iconRegister.registerIcon(ModStrings.MOD_ID.toLowerCase() + ":usedCard");
    }
	
	@Override
    public Icon getIconFromDamage(int i)
	{
		return iconArray[i];
	}
	private int tick = 0;

	@Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world,
            EntityPlayer player)
    {
		if(encoded(itemStack))
		{
			List<ItemStack> inputs = new ArrayList<ItemStack>();
			for(int i = 0; i < 9; i++)
			{
				ItemStack input = getInputs(itemStack, i);
				if(input != null)
				{
					inputs.add(input);
				}
			}
			if(checkInventory(inputs, player))
			{
				for(ItemStack invent : player.inventory.mainInventory)
					for(ItemStack item : inputs)
					{
						if(invent != null && (invent.itemID == item.itemID && 
							invent.getItemDamage() == item.getItemDamage()))
						{
							player.inventory.consumeInventoryItem(item.itemID);
						}
					}
				ItemStack outputStack = getOuput(itemStack);
				player.inventory.addItemStackToInventory(outputStack);
				itemStack.stackSize--;
				return itemStack;
			}
			else
			{
				tick++;
				if(tick > 1)
				{
					player.addChatMessage("Missing some items!");
					tick = 0;
				}
				return itemStack;
			}
		}
		return itemStack;
    }
	
	public boolean checkInventory(List<ItemStack> list, EntityPlayer player)
	{
		int i = 0;
		for(ItemStack invent : player.inventory.mainInventory)
			for(ItemStack item : list)
			{
				if(invent != null && (invent.itemID == item.itemID && 
					invent.getItemDamage() == item.getItemDamage()))
				{
					i++;
				}
			}
		
		if(i >= list.size())
			return true;
		else
			return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player,
            List infoList, boolean bool)
    {
		if(encoded(itemStack))
		{
			infoList.add(getOuput(itemStack).getDisplayName());
		}
    }
}
