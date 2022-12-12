package game.settings;

public class GameSettings {

    private boolean debugMode;
    private double gameSpeedMultiplier;
    private AudioSettings audioSettings;
    public static Language language;

    public GameSettings(boolean debugMode, Language language) {
        this.language = language;
        this.debugMode = debugMode;
        gameSpeedMultiplier = 1;
        audioSettings = new AudioSettings();
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void toggleDebugMode() {
        debugMode = !debugMode;
    }

    public void increaseGameSpeed() {
        gameSpeedMultiplier += 0.25;
    }
    
    public void decreaseGameSpeed() {
        gameSpeedMultiplier -= 0.25;
    }

    public double getGameSpeedMultiplier() {
        if (gameSpeedMultiplier <= 0) {
            gameSpeedMultiplier = 0.25;
        }
        return gameSpeedMultiplier;
    }

    public AudioSettings getAudioSettings() {
        return audioSettings;
    }

    public static Language getLanguage() {
        return language;
    }

    public enum Language{
        PORTUGUESE, ENGLISH;
    }
    
}
