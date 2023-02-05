package entity.player;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import lines.Line;
import lines.LinesCatalog;
import lines.NPCLine;
import lines.PlayerLine;
import state.counter.StagesEnum;
import state.counter.WorkCounterState;

public class PlayerSpeech {
    private List<NPCLine> npcAnswers;
    private List<PlayerLine> playerLines;
    private List<PlayerLine> playerQuickLines;
    private LinesCatalog lines;

    public PlayerSpeech(WorkCounterState state){
        npcAnswers = new ArrayList<>();
        playerLines = new ArrayList<>();
        playerQuickLines = new ArrayList<>();
        lines = state.getLinesCatalog();
    }

    public void getFirstLines(WorkCounterState state, StagesEnum stage) {
        for (Line startLine : lines.getStartingList()) {
            if (startLine instanceof PlayerLine && startLine.getStage() == stage) {
                playerLines.add(((PlayerLine)startLine));
            }
        }
        for (PlayerLine playerLine : playerLines) {
            if (playerLine.isQuickAnswer()) {
                playerQuickLines.add(playerLine);
            }
        }
        state.getQuickAnswers().generateQuickAnswer(state);
    }

    public void setLines(List<Integer> ids, WorkCounterState state) {
        for (Line line : lines.getFullLineList()) {
            if (line instanceof PlayerLine && ids.contains(line.getId())) {
                playerLines.add(((PlayerLine)line));
                if (((PlayerLine)line).isQuickAnswer()) {
                    playerQuickLines.add(((PlayerLine)line));
                }
            }
        }
        state.getQuickAnswers().generateQuickAnswer(state);
    }

    public void setPlayerLines(List<PlayerLine> playerLines) {
        this.playerLines = playerLines;
    }

    public List<PlayerLine> getPlayerQuickLines() {
        return playerQuickLines;
    }

    public void clearPlayerResponses(){
        playerLines = new ArrayList<>();
        playerQuickLines = new ArrayList<>();
    }

    public List<PlayerLine> getPlayerLines() {
        return playerLines;
    }
}
