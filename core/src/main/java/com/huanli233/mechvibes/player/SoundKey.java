package com.huanli233.mechvibes.player;

import net.labymod.api.client.resources.ResourceLocation;

public class SoundKey {

    private final Sound sound;
    private boolean pressed;

    public SoundKey(String mode, String key) {
        sound = new Sound();
        try {
            sound.loadClip(ResourceLocation.create("mechvibes", "sounds/" + mode + "/" + key + ".wav"));
        } catch (Exception ignored) {
        }

        pressed = false;
    }

    public void play() {
        sound.play();
    }

    public void setVolume(float volume) {
        sound.setVolume(volume);
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}
