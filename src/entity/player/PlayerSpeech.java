package entity.player;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import lines.Line;
import lines.LinesCatalog;
import lines.NPCLine;
import lines.PlayerLine;
import state.counter.StagesEnum;

public class PlayerSpeech {
    private List<NPCLine> npcAnswers;
    private List<PlayerLine> playerLines;
    private List<PlayerLine> playerQuickLines;
    private LinesCatalog lines;
    private boolean canSpeak;

    public PlayerSpeech(LinesCatalog linesCatalog){
        npcAnswers = new ArrayList<>();
        playerLines = new ArrayList<>();
        playerQuickLines = new ArrayList<>();
        lines = linesCatalog;
        canSpeak = false;
        getFirstLines();
    }

    private void getFirstLines() {
        for (Line startLine : lines.getStartingList()) {
            if (startLine instanceof PlayerLine && startLine.getStage() == StagesEnum.GREETING) {
                playerLines.add(((PlayerLine)startLine));
            }
        }
        for (PlayerLine playerLine : playerLines) {
            if (playerLine.isQuickAnswer()) {
                playerQuickLines.add(playerLine);
            }
        }
    }

    public void setCanSpeak(boolean canSpeak) {
        this.canSpeak = canSpeak;
    }

    public boolean canSpeak() {
        return canSpeak;
    }

    public void update(State state, PlayerLine chosenLine){

    }

    public void setPlayerLines(List<PlayerLine> playerLines) {
        this.playerLines = playerLines;
    }

    public List<PlayerLine> getPlayerQuickLines() {
        return playerQuickLines;
    }
}
