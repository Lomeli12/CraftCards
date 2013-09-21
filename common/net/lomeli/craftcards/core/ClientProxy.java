package net.lomeli.craftcards.core;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import net.lomeli.craftcards.core.handler.VersionCheckTickHandler;
import net.lomeli.craftcards.tileentity.TileEntityStamper;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityStamper.class,
                "craftCardsStamper");
    }

    @Override
    public void registerTickHandler() {
        TickRegistry.registerTickHandler(new VersionCheckTickHandler(),
                Side.CLIENT);
    }
}
