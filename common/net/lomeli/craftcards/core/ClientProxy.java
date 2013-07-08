package net.lomeli.craftcards.core;

import net.lomeli.craftcards.tileentity.TileEntityStamper;
import cpw.mods.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityStamper.class, "craftCardsStamper");
	}

}
