package game;

public class Time {
    private int updatesSinceStart;

    public Time() {
        updatesSinceStart = 0;
    }

    public int getUpdatesFromSeconds(int seconds) {
        return seconds * GameLoop.UPDATES_PER_SECOND;
    }

    public void update() {
        updatesSinceStart++;
    }

    public String getFormattedTime() {
        StringBuilder stringBuilder = new StringBuilder();
        int minutes = updatesSinceStart / GameLoop.UPDATES_PER_SECOND / 60;
        int seconds = updatesSinceStart / GameLoop.UPDATES_PER_SECOND % 60; // gets the remainder from the conversion to minutes to show

        if (minutes < 10 ) {
            stringBuilder.append(0);
        }
        stringBuilder.append(minutes);
        stringBuilder.append(":");

        if (seconds < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(seconds);

        return stringBuilder.toString();
    }
    
}
