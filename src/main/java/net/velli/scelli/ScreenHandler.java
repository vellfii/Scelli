package net.velli.scelli;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class ScreenHandler {
    private static Screen screen;
    public static void openScreen(Screen screen) {
        ScreenHandler.screen = screen;
    }

    public static void tick() {
        if (ScreenHandler.screen == null) return;
        MinecraftClient.getInstance().setScreen(ScreenHandler.screen);
        ScreenHandler.screen = null;
    }

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick());
    }
}
