package state.counter.ui;

import java.util.ArrayList;
import java.util.List;

import core.Position;
import state.State;
import state.counter.WorkCounterState;
import ui.clickable.IClickAction;
import ui.object.ClickableWord;

public class QuickAnswer extends ClickableWord{

    public QuickAnswer(Position position, int answerNumber, State state, List<String> phrase, int initialOpacity, int renderOrder,
            IClickAction clickEvent) {
        super(position, answerNumber, state, phrase, initialOpacity, renderOrder, clickEvent);
    }

    @Override
    public void update(State state) {
        super.update(state);
        if (disappear) {
            opacity -= speed;
            if (opacity <= 0) {
                opacity = 0;
                disappear = false;
                state.getGameObject().remove(this);
                if (state instanceof WorkCounterState) {
                    ((WorkCounterState)state).getQuickAnswers().setQuickAnswers(new ArrayList<>());
                }
            }
            setOpacity(state.getSpriteLibrary(), opacity);
        }
        if (fadeIn) {
            opacity += speed;
            if (opacity > 1) {
                opacity = 1.0f;
                fadeIn = false;
            }
            setOpacity(state.getSpriteLibrary(), opacity);
        }
        if (fadeOut) {
            opacity -= speed;
            if (opacity <= 0) {
                opacity = 0.0f;
                fadeOut = false;
                canClick = false;
            }
            setOpacity(state.getSpriteLibrary(), opacity);
        }
    }
    
}
