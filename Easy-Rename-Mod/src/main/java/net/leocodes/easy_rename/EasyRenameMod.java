package net.leocodes.easy_rename;

import net.fabricmc.api.ModInitializer;
import net.leocodes.easy_rename.util.ModRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyRenameMod implements ModInitializer {
	public static final String MOD_ID = "easy_rename";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	// A Comment
	@Override
	public void onInitialize() {
		ModRegistries.registerModStuff();
	}
}
