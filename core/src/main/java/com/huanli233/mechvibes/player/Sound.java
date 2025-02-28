package com.huanli233.mechvibes.player;

import net.labymod.api.client.resources.ResourceLocation;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;

public class Sound {
    private Clip clip;

    public void loadClip(ResourceLocation location) throws Exception {
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new BufferedInputStream(location.openStream())));
    }

    public void play() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void setVolume(float volume) {
        if (clip == null) {
            return;
        }

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

}
