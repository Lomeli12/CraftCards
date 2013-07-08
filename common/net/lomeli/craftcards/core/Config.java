package net.lomeli.craftcards.core;

import java.io.File;

import net.lomeli.craftcards.lib.ModInts;

import net.minecraftforge.common.Configuration;

public class Config
{

	public static void configureMod(File configName)
	{
		Configuration config = new Configuration(configName);
		
		config.load();
		
		ModInts.craftCardID = config.get("Items", "carftCard", 890).getInt(890);
		ModInts.inkID = config.get("Items", "ink", 891).getInt(891);
		
		ModInts.stamperID = config.get("Blocks", "stamper", 892).getInt(892);
		
		config.save();
	}
}
