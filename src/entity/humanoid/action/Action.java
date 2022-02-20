package entity.humanoid.action;

import audio.AudioPlayer;
import entity.humanoid.Humanoid;
import state.State;

public abstract class Action {

    protected boolean soundPlaying;

    public abstract void update(State state, Humanoid humanoid);
    public abstract boolean isDone();
    public abstract String getAnimationName();
    public abstract String getSoundName();
    public void playSound(AudioPlayer audioPlayer) {
        if (!soundPlaying && getSoundName() != null) {
            audioPlayer.playSound(getSoundName());
            soundPlaying = true;
        }
    }
}
