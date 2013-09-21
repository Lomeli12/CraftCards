package net.lomeli.craftcards.items;

import java.util.List;

import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.craftcards.lib.ModStrings;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemCraftCards extends Item implements ICard {
    public Icon[] iconArray;

    public ItemCraftCards(int id) {
        super(id);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public static void setOutput(ItemStack itemStack, ItemStack output) {
        NBTUtil.setInteger(itemStack, "OutItemID", output.itemID);
        NBTUtil.setInteger(itemStack, "OutItemMeta", output.getItemDamage());
        NBTUtil.setInteger(itemStack, "OutItemSize", output.stackSize);
    }

    public static void encode(ItemStack itemStack) {
        NBTUtil.setBoolean(itemStack, "encoded", true);
    }

    public static boolean encoded(ItemStack itemStack) {
        return NBTUtil.getBoolean(itemStack, "encoded");
    }

    public ItemStack getOuput(ItemStack itemStack) {
        int id = NBTUtil.getInt(itemStack, "OutItemID");
        int meta = NBTUtil.getInt(itemStack, "OutItemMeta");
        int size = NBTUtil.getInt(itemStack, "OutItemSize");
        return new ItemStack(id, size, meta);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[2];

        iconArray[0] = iconRegister.registerIcon(ModStrings.MOD_ID
                .toLowerCase() + ":newCard");
        iconArray[1] = iconRegister.registerIcon(ModStrings.MOD_ID
                .toLowerCase() + ":usedCard");
    }

    @Override
    public Icon getIconFromDamage(int i) {
        return iconArray[i];
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world,
            EntityPlayer player) {
        if(player != null && world != null && itemStack != null) {
            ItemStack out = this.getOuput(itemStack);
            if(out != null) {
                player.inventory.addItemStackToInventory(out);
                itemStack.stackSize--;
            }
        }
        return itemStack;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player,
            List infoList, boolean bool) {
        if(encoded(itemStack)) {
            infoList.add(getOuput(itemStack).getDisplayName());
        }
    }

    @Override
    public ItemStack output(ItemStack stack) {
        return getOuput(stack);
    }

}
