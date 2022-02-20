package audio;

import javax.sound.sampled.Clip;

import game.settings.AudioSettings;

public class MusicClip extends AudioClip {

    public MusicClip(Clip clip) {
        super(clip);
    }

    @Override
    protected float getVolume(AudioSettings audioSettings) {
        return audioSettings.getMusicVolume();
    }
    
}
