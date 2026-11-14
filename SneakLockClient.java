package com.example.sneaklock;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SneakLockClient implements ClientModInitializer {
    public static boolean latched = false;
    private static boolean waitForRelease = false;
    private static KeyBinding stopKey;

    @Override
    public void onInitializeClient() {
        stopKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "SneakLock - Stop Sneaking",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (stopKey.wasPressed()) {
                latched = false;
                waitForRelease = true;
            }
        });
    }

    public static void update(boolean sneakKeyDown) {
        if (!sneakKeyDown) {
            waitForRelease = false;
        } else if (!waitForRelease) {
            latched = true;
        }
    }
}
