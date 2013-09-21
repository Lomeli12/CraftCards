package net.lomeli.craftcards.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.lomeli.craftcards.items.ModItems;
import net.lomeli.craftcards.lib.ModInts;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModBlocks {
    public static Block stamper;

    public static void registerBlocks() {
        stamper = new BlockStamper(ModInts.stamperID).setUnlocalizedName(
                "Stamper").setCreativeTab(CreativeTabs.tabDecorations);

        GameRegistry.registerBlock(stamper, "Card Stamper");

        LanguageRegistry.addName(stamper, "Card Stamper");

        GameRegistry.addShapedRecipe(new ItemStack(stamper), new Object[] {
                "ICI", "STS", "SSS", 'S', Block.cobblestone, 'T',
                Block.workbench, 'I', ModItems.ink, 'C', ModItems.craftCard });
    }

}
