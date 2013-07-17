package net.lomeli.craftcards.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.lomeli.craftcards.lib.ModInts;
import net.lomeli.craftcards.lib.ModStrings;

import net.lomeli.lomlib.item.ItemGeneric;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItems
{
	public static Item craftCard, ink, cardBag, cloth;
	
	public static void registerItems()
	{
		craftCard = new ItemCraftCards(ModInts.craftCardID)
			.setUnlocalizedName("craftcard");
		ink = new ItemGeneric(ModInts.inkID, ModStrings.MOD_ID, "ink").setUnlocalizedName("ink")
			.setCreativeTab(CreativeTabs.tabMaterials);
		cardBag = new ItemCardBag(ModInts.cardBagID).setUnlocalizedName("cardbag");
		cloth = new ItemGeneric(ModInts.clothID, ModStrings.MOD_ID, "cloth").setUnlocalizedName("simplecloth")
			.setCreativeTab(CreativeTabs.tabMaterials);
		
		LanguageRegistry.addName(craftCard, "Craft Card");
		LanguageRegistry.addName(ink, "Ink");
		LanguageRegistry.addName(cardBag, "Card Bag");
		LanguageRegistry.addName(cloth, "Simple Cloth");
		
		GameRegistry.addShapelessRecipe(new ItemStack(ink), new Object[]{Item.glassBottle, Item.dyePowder});
		GameRegistry.addShapelessRecipe(new ItemStack(craftCard), new Object[]{Item.paper, ink});
		GameRegistry.addShapelessRecipe(new ItemStack(craftCard), new Object[]{new ItemStack(craftCard, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(cloth), new Object[]{Item.silk, Item.silk, Item.silk});
		
		GameRegistry.addRecipe(new ItemStack(cardBag), new Object[]{
			"SCS","CDC"," C ", 'C', cloth, 'S',Item.silk, 'D', new ItemStack(Item.dyePowder, 1, 2)});
	}
}
