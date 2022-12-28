package state.counter.ui;

import java.util.ArrayList;
import java.util.List;

import core.Position;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
import lines.PlayerLine;
import state.counter.WorkCounterState;

public class QuickAnswerManager {
    private List<QuickAnswer> quickAnswers;
    private List<Position> positions;
    private boolean addedObject;

    public QuickAnswerManager(WorkCounterState state){
        positions = new ArrayList<>();
        quickAnswers = new ArrayList<>();
        generatePositions();
        generateQuickAnswer(state);
    }

    private void generatePositions() {
        positions.add(new Position(160, 150));
        positions.add(new Position(200, 50));
        positions.add(new Position(30, 25));
        positions.add(new Position(40, 50));
    }

    private void generateQuickAnswer(WorkCounterState state) {
        for (int i = 0; i < 3; i++) {
            if (state.getPlayerChoices().getPlayerQuickLines().size() <= i) {
                break;
            }
            if (GameSettings.language == Language.PORTUGUESE) {
                PlayerLine playerLine = state.getPlayerChoices().getPlayerQuickLines().get(i);
                quickAnswers.add(new QuickAnswer(positions.get(i), i, state, GameSettings.language == Language.PORTUGUESE? playerLine.getPtLine() : playerLine.getEngLine(), 5, (localState) -> {System.out.println("clicked");}));
            }
        }
    }

    public void update(WorkCounterState state){
        if (state.getPlayerChoices().canSpeak()) {
            if (!addedObject) {
                for (QuickAnswer quickAnswer : quickAnswers) {
                    state.getGameObject().add(quickAnswer);
                }
                addedObject = true;            
            }
        }
        for (QuickAnswer quickAnswer : quickAnswers) {
            quickAnswer.update(state);
        }
    }
}
