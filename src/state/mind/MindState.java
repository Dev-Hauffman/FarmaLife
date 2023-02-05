package state.mind;

import core.Position;
import core.Size;
import entity.GameObject;
import entity.TransitionObject;
import game.Game;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
import input.Input;
import lines.ActionProcessor;
import state.State;
import state.counter.WorkCounterState;
import state.mind.ui.AnswerDisplay;
import state.mind.ui.AnswerPool;
import text.GameText;
import ui.object.ButtonObject;
import ui.object.ClickableWord;

public class MindState extends State{
    private WorkCounterState workState;
    private AnswerPool answerPool;
    private AnswerDisplay answerDisplay;

    public MindState(Size windowSize, Input input, GameSettings gameSettings, WorkCounterState state){
        super(windowSize, input, gameSettings);
        workState = state;
        answerPool = new AnswerPool(state.getPlayerChoices().getPlayerLines(), this);
        answerDisplay = new AnswerDisplay(new Position(300, 625), new Size(1000, 125), state, 12);
        initializeUI();
    }

    private void initializeUI() {
        String ptQuestion = "O QUE DIZER?";
        String engQuestion = "WHAT TO SAY?";
        GameText question = new GameText(GameSettings.language == Language.PORTUGUESE? ptQuestion : engQuestion, this, "testFont", new Position(500, 50), 42, 2);
        gameObjects.add(question);
        gameObjects.add(answerDisplay);
        gameObjects.add(answerPool);
        String ptConfirm = "DIZER ISSO";
        String engConfirm = "SAY IT";
        ButtonObject sayIt = new ButtonObject(GameSettings.language == Language.PORTUGUESE? ptConfirm : engConfirm, new Size(250, 50), this, new Position(700, 800), 2, false, (localState) -> {
            System.out.println("clicked");
            if (answerPool.getSelectedLine() != null) {
                ActionProcessor.process(answerPool.getSelectedLine(), workState);
                workState.getQuickAnswers().removeQuickAnswers(localState);
                workState.getPlayerChoices().clearPlayerResponses();
                workState.getSpeechDisplay().clearDisplay(localState);
                workState.getActivePatient().getAiResponse().speak(workState, answerPool.getSelectedLine());
            }
            TransitionObject transition = null;
            for (GameObject gameObject : workState.getGameObject()) {
                if (gameObject instanceof TransitionObject) {
                    transition = (TransitionObject)gameObject;
                }
            }
            workState.getGameObject().remove(transition);
            this.setNextState(workState);
        });
        gameObjects.add(sayIt);
    }

    @Override
    public void update(Game game) {
        super.update(game);
    }

    public AnswerDisplay getAnswerDisplay() {
        return answerDisplay;
    }
}
