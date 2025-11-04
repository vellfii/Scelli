package net.velli.scelli;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scelli implements ClientModInitializer {
    public static MinecraftClient MC = MinecraftClient.getInstance();
    public static final String MOD_ID = "scelli";
    public static final Logger LOGGER = LoggerFactory.getLogger("Scelli");

    @Override
    public void onInitializeClient() {
        ScreenHandler.init();
        DebugScreenCmd.init();
        LOGGER.info("DOING SHIT AND STUFF! YEEEAHHH");
    }
}
