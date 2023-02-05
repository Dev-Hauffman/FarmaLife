package state.counter.ui;

import java.util.ArrayList;
import java.util.List;

import core.Position;
import core.Size;
import entity.AnimatedObject;
import entity.TransitionObject;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
import lines.ActionProcessor;
import lines.PlayerLine;
import state.State;
import state.counter.WorkCounterState;
import state.mind.MindState;

public class QuickAnswerManager {
    private List<QuickAnswer> quickAnswers;
    private List<Position> positions;
    private int ttl;

    public QuickAnswerManager(WorkCounterState state){
        positions = new ArrayList<>();
        quickAnswers = new ArrayList<>();
        ttl = 50;
        generatePositions();
    }

    private void generatePositions() {
        positions.add(new Position(160, 150));
        positions.add(new Position(200, 50));
        positions.add(new Position(450, 50));
        positions.add(new Position(550, 150));
    }

    public void generateQuickAnswer(WorkCounterState state) {
        for (int i = 0; i < 3; i++) {
            if (state.getPlayerChoices().getPlayerQuickLines().size() <= i) {
                break;
            }
            PlayerLine playerLine = state.getPlayerChoices().getPlayerQuickLines().get(i);
            QuickAnswer option = new QuickAnswer(positions.get(i), i, state, GameSettings.language == Language.PORTUGUESE? playerLine.getPtLine() : playerLine.getEngLine(), 1, 5, (localState) -> {
                    if (localState instanceof WorkCounterState) {
                        removeQuickAnswers(state);
                        ((WorkCounterState)localState).getPlayerChoices().clearPlayerResponses();
                        ((WorkCounterState)localState).getSpeechDisplay().clearDisplay(state);
                        ActionProcessor.process(playerLine, state);
                        ((WorkCounterState)localState).getActivePatient().getAiResponse().speak(state, playerLine);
                    }
                }
            );
            quickAnswers.add(option);
        }
        List<String> forthOptionPt = new ArrayList<>();
        forthOptionPt.add("[PENSAR");
        forthOptionPt.add("NO");
        forthOptionPt.add("QUE");
        forthOptionPt.add("FALAR");
        List<String> forthOptionEng = new ArrayList<>();
        forthOptionEng.add("[THINK");
        forthOptionEng.add("ABOUT");
        forthOptionEng.add("WHAT");
        forthOptionEng.add("TO");
        forthOptionEng.add("SAY]");
        QuickAnswer forthOption = new QuickAnswer(positions.get(3), 3, state, GameSettings.language == Language.PORTUGUESE? forthOptionPt : forthOptionEng, 1, 5, (localState) -> {
                TransitionObject closingEyes = new TransitionObject(state.getSpriteLibrary(), 10);
                state.getGameObject().add(closingEyes);
                closingEyes.playAnimation(false);
            }
        );
        if (state.getPlayerChoices().getPlayerLines().size() > 0) {
            quickAnswers.add(forthOption);
        }
    }

    public void update(WorkCounterState state){
        if (!quickAnswers.isEmpty()) {
            if (ttl >= 0) {
                ttl--;
            }                
            for (QuickAnswer quickAnswer : quickAnswers) {
                quickAnswer.update(state);
            }                
        }
    }

    public void removeQuickAnswers(State state){
        for (QuickAnswer word : quickAnswers) {
            word.disappear(0.2f);
        }
    }

    public void addQuickAnswers(State state){
        for (QuickAnswer answer : quickAnswers) {
            state.getGameObject().add(answer);
        }
    }

    public void setQuickAnswers(List<QuickAnswer> quickAnswers) {
        this.quickAnswers = quickAnswers;
    }
}
