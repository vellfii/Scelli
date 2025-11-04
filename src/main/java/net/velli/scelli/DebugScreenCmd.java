package net.velli.scelli;

import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class DebugScreenCmd {
    public static void init() {
        registerCommand(context -> {
            ScreenHandler.openScreen(new DebugScreen(Scelli.MC.currentScreen));
            return 1;
        });
    }


    private static void registerCommand(Command<FabricClientCommandSource> command) {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("debugscreen").executes(command));
        });
    }
}
