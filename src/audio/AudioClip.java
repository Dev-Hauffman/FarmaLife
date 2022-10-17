package audio;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import game.settings.AudioSettings;

public abstract class AudioClip {

    private final Clip CLIP;

    public AudioClip(Clip clip) {
        this.CLIP = clip;
        clip.start();
    }

    public void update(AudioSettings audioSettings) {
        setVolume(audioSettings);
    }

    public void setVolume(AudioSettings audioSettings) {
        final FloatControl control = (FloatControl) CLIP.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range * getVolume(audioSettings)) + control.getMinimum();
        control.setValue(gain);
    }

    protected abstract float getVolume(AudioSettings audioSettings);

    public boolean hasFinishedPlaying() {
        return !CLIP.isRunning();
    }

    public void cleanUp() {
        CLIP.close();
    }
    
}
