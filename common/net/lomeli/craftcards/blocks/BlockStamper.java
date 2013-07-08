package net.lomeli.craftcards.blocks;

import java.util.Random;

import net.lomeli.craftcards.CraftCards;
import net.lomeli.craftcards.lib.ModInts;
import net.lomeli.craftcards.lib.ModStrings;
import net.lomeli.craftcards.tileentity.TileEntityStamper;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockStamper extends BlockContainer
{
	public Icon[] iconArray;
	
	public BlockStamper(int par1)
    {
	    super(par1, Material.wood);
    }
	
	@Override
    public void registerIcons(IconRegister iconRegister)
    {
		iconArray = new Icon[3];
		
		iconArray[0] = iconRegister.registerIcon(ModStrings.MOD_ID.toLowerCase() + ":stamper_bottom");
		iconArray[1] = iconRegister.registerIcon(ModStrings.MOD_ID.toLowerCase() + ":stamper_top");
		iconArray[2] = iconRegister.registerIcon(ModStrings.MOD_ID.toLowerCase() + ":stamper_side");
    }
	
	@Override
    public Icon getIcon(int side, int metadata)
    {
		return side == 0 ? iconArray[0] : side == 1 ? iconArray[1] : iconArray[2];
    }

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int i, float f, float g, float t)
    {
        if (player.isSneaking())
            return false;
        else
        {
            if (!world.isRemote)
            {
            	TileEntityStamper tileEntity = (TileEntityStamper)world.getBlockTileEntity(x, y, z);
            	if(tileEntity != null)
            		player.openGui(CraftCards.instance, ModInts.stamperGuiID, world, x, y, z);
            }
        }
        return true;
    }
	
	@Override
    public TileEntity createNewTileEntity(World world)
    {
	    return new TileEntityStamper();
    }
	
	@Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

	@Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {

        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }
	
    private Random rand = new Random();

    private void dropInventory(World world, int x, int y, int z)
    {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0)
            {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z
                        + dZ, new ItemStack(itemStack.itemID,
                        itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound(
                            (NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
}
