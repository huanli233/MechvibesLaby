package com.huanli233.mechvibes.listener;

import com.huanli233.mechvibes.MechvibesAddon;
import com.huanli233.mechvibes.MechvibesConfiguration;
import com.huanli233.mechvibes.player.SoundKey;
import com.huanli233.mechvibes.player.Sound;
import com.huanli233.mechvibes.referenceable.InputHelper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class MechvibesTickListener {

    private final MechvibesAddon addon;

    private MechvibesConfiguration.KeyboardMode lastKeyboardMode;
    private float lastKeyboardVolume;
    private float lastMouseVolume;

    private final Sound mouseLeftSound = new Sound();
    private final Sound mouseRightSound = new Sound();

    private final HashMap<Integer, SoundKey> keyMap = new HashMap<>();
    private boolean mouseLeftPress, mouseRightPress;

    public MechvibesTickListener(MechvibesAddon addon) {
        this.addon = addon;
        lastKeyboardMode = addon.configuration().keyboardMode.getOrDefault();
        Thread thread = new Thread(() -> {
            loadKeyboardSounds(addon.configuration().keyboardMode.getOrDefault().getValue());
            loadMouseSounds();
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (event.phase() != Phase.PRE) {
            return;
        }
        MechvibesConfiguration config = addon.configuration();
        if (config.enabled().getOrDefault()) {

            InputHelper inputHelper = addon.getInputHelper();
            String mode = config.keyboardMode.getOrDefault().getValue();

            if (!Objects.equals(lastKeyboardMode.getValue(), mode)) {
                lastKeyboardMode = config.keyboardMode.getOrDefault();
                loadKeyboardSounds(mode);
            }

            if (lastKeyboardVolume != config.keyboardVolume.getOrDefault()) {
                lastKeyboardVolume = config.keyboardVolume.getOrDefault();
                for (Map.Entry<Integer, SoundKey> map : keyMap.entrySet()) {
                    map.getValue().setVolume(lastKeyboardVolume);
                }
            }
            if (lastMouseVolume != config.mouseVolume.getOrDefault()) {
                lastMouseVolume = config.mouseVolume.getOrDefault();
                mouseLeftSound.setVolume(lastMouseVolume);
                mouseRightSound.setVolume(lastMouseVolume);
            }

            for (Map.Entry<Integer, SoundKey> map : keyMap.entrySet()) {
                if (map.getValue().isPressed() && !inputHelper.isKeyDown(map.getKey())) {
                    map.getValue().setPressed(false);
                }
            }

            if (config.keyboard.getOrDefault()) {
                for (int keyCode = 0; keyCode < 256; keyCode++) {
                    if (inputHelper.isKeyDown(keyCode)) {
                        if (keyMap.get(keyCode) != null) {
                            SoundKey key = keyMap.get(keyCode);
                            if (!key.isPressed()) {
                                key.play();
                                key.setPressed(true);
                            }
                        }
                    }
                }
            }

            if (config.mouse.getOrDefault()) {
                if (inputHelper.isButtonDown(0) && !mouseLeftPress) {
                    mouseLeftPress = true;
                    mouseLeftSound.play();
                }

                if (!inputHelper.isButtonDown(0) && mouseLeftPress) {
                    mouseLeftPress = false;
                }

                if (inputHelper.isButtonDown(1) && !mouseRightPress) {
                    mouseRightPress = true;
                    mouseRightSound.play();
                }

                if (!inputHelper.isButtonDown(1) && mouseRightPress) {
                    mouseRightPress = false;
                }
            }
        }
    }

    private void loadKeyboardSounds(String type) {
        for (int keyCode = 0; keyCode < 256; keyCode++) {

            if (keyCode == 15) {
                // KEY_TAB
                keyMap.put(15, new SoundKey(type, "tab"));
                continue;
            }

            if (keyCode == 14) {
                keyMap.put(14, new SoundKey(type, "backspace"));
                continue;
            }

            if (keyCode == 58) {
                keyMap.put(58, new SoundKey(type, "capslock"));
                continue;
            }

            if (keyCode == 28) {
                keyMap.put(28, new SoundKey(type, "enter"));
                continue;
            }

            if (keyCode == 57) {
                // KEY_SPACE
                keyMap.put(57, new SoundKey(type, "space"));
                continue;
            }

            if (keyCode == 42) {
                // KEY_LSHIFT
                keyMap.put(42, new SoundKey(type, "shift"));
                continue;
            }

            if (keyCode == 54) {
                // KEY_RSHIFT
                keyMap.put(54, new SoundKey(type, "shift"));
                continue;
            }

            keyMap.put(keyCode, new SoundKey(type, String.valueOf(ThreadLocalRandom.current().nextInt(1, 6))));
        }
    }

    private void loadMouseSounds() {
        try {
            mouseLeftSound.loadClip(ResourceLocation.create("mechvibes", "sounds/mouse.wav"));
            mouseRightSound.loadClip(ResourceLocation.create("mechvibes", "sounds/mouse.wav"));
        } catch (Exception ignored) {}
    }
}
