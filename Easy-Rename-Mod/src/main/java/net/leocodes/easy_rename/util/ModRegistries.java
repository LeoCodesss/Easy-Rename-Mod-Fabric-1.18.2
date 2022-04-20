package net.leocodes.easy_rename.util;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.leocodes.easy_rename.EasyRenameMod;
import net.leocodes.easy_rename.command.RenameCommand;

public class ModRegistries {
    public static void registerModStuff() {
        registerCommands();
    }

    private static void registerCommands() {
        System.out.println("Registering commands for " + EasyRenameMod.MOD_ID);
        CommandRegistrationCallback.EVENT.register(RenameCommand::register);
    }
}
