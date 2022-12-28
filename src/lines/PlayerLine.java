package lines;

import java.util.ArrayList;
import java.util.List;

public class PlayerLine extends Line{
    private int satisfactionRating;
    private boolean quickAnswer;
    private List<Integer> streakLines;

    public PlayerLine(){
        streakLines = new ArrayList<>();
    }

    public void setSatisfactionRating(int satisfactionRating) {
        this.satisfactionRating = satisfactionRating;
    }

    public void setQuickAnswer(boolean quickAnswer) {
        this.quickAnswer = quickAnswer;
    }

    public int getSatisfactionRating() {
        return satisfactionRating;
    }

    public boolean isQuickAnswer() {
        return quickAnswer;
    }

    public List<Integer> getStreakLines() {
        return streakLines;
    }
}
