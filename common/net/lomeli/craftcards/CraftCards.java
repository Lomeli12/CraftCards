package net.lomeli.craftcards;

import net.lomeli.craftcards.blocks.ModBlocks;
import net.lomeli.craftcards.core.CommonProxy;
import net.lomeli.craftcards.core.Config;
import net.lomeli.craftcards.core.handler.GuiHandler;
import net.lomeli.craftcards.items.ModItems;
import net.lomeli.craftcards.lib.ModInts;
import net.lomeli.craftcards.lib.ModStrings;

import net.lomeli.lomlib.util.UpdateHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = ModStrings.MOD_ID, name = ModStrings.MOD_NAME, version = ModStrings.VERSION,
	dependencies="required-after:LomLib@[1.0.4,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CraftCards
{
	@SidedProxy(clientSide = ModStrings.CLIENT, serverSide = ModStrings.COMMON)
	public static CommonProxy proxy;
	
	@Instance(ModStrings.MOD_ID)
	public static CraftCards instance;

	private GuiHandler gui = new GuiHandler();
	
	public static UpdateHelper updater = new UpdateHelper();
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		Config.configureMod(event.getSuggestedConfigurationFile());
		
		try
		{
			updater.check(ModStrings.MOD_NAME, ModStrings.XMLURL, 
				ModInts.MAJOR, ModInts.MINOR, ModInts.BUILD);
		} catch (Exception e)
		{}
		
		ModItems.registerItems();
		ModBlocks.registerBlocks();
		
		proxy.registerTickHandler();
    }
	
	@Mod.EventHandler
    public void main(FMLInitializationEvent event)
    {
		NetworkRegistry.instance().registerGuiHandler(this, gui);
		
		proxy.registerTileEntities();
    }

	@Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event)
    {
		
    }
}
