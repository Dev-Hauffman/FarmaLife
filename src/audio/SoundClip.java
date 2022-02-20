package audio;

import javax.sound.sampled.Clip;

import game.settings.AudioSettings;

public class SoundClip extends AudioClip {

    public SoundClip(Clip clip) {
        super(clip);
    }

    @Override
    protected float getVolume(AudioSettings audioSettings) {
        return audioSettings.getSoundVolume();
    }
    
}
