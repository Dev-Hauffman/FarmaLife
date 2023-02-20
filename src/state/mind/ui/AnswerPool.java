package state.mind.ui;

import java.awt.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Position;
import core.Size;
import entity.GameObject;
import game.settings.GameSettings;
import game.settings.GameSettings.Language;
import gfx.ImageUtils;
import gfx.SpriteLibrary;
import lines.LinesCatalog;
import lines.PlayerLine;
import state.State;
import state.mind.MindState;
import text.GameText;
import ui.object.ClickableWord;
import ui.object.UIObject;

public class AnswerPool extends UIObject{
    private Rectangle spawningZone;
    private Image sprite;
    private List<PlayerLine> poolLines;
    private boolean[] inPool;
    private List<GameObject> toRemove;
    private List<HistoryItem> history;
    private boolean canPopulate;
    private PlayerLine selectedLine;

    public AnswerPool(List<PlayerLine> playerLines, MindState state){
        super();
        this.poolLines = playerLines;
        toRemove = new ArrayList<>();
        history = new ArrayList<>();
        renderOrder = 10;
        spawningZone = new Rectangle(200, 150, 900, 350);
        inPool = new boolean[playerLines.size()];
        populate(playerLines, state);
        loadGraphics(state.getSpriteLibrary());
    }

    public void populate(List<PlayerLine> playerLines, MindState state) {
        removePoolItems();
        Random random = new Random();
        boolean[] alreadyChosen = new boolean[playerLines.size()];
        for (int i = 0; i < playerLines.size(); i++) {
            int chosenNumber = random.nextInt(playerLines.size());
            // System.out.println("chosen number: " + chosenNumber);
            if(!alreadyChosen[chosenNumber] && !inPool[chosenNumber]){
                alreadyChosen[chosenNumber] = true;
                PlayerLine currentPlayerLine = playerLines.get(chosenNumber);
                PoolItem spawnItem = new PoolItem(new Position(0, 0), -1, state, GameSettings.language == Language.PORTUGUESE? currentPlayerLine.getPtLine() : currentPlayerLine.getEngLine(), 0, 5, (localState) -> {
                    System.out.println("this clicked");
                    if (state instanceof MindState) {
                        PoolItem target = null;
                        for (GameObject gameObject : List.copyOf(children)) {
                            if(((PoolItem)gameObject).isClicked()) {
                                target = (PoolItem)gameObject;
                            }
                        }
                        if (target != null) {
                            target.setStop(!target.isStopped());
                            if (target.isStopped()) {
                                selectedLine = target.getLine();
                                List<String> line = GameSettings.language == Language.PORTUGUESE? currentPlayerLine.getPtLine() : currentPlayerLine.getEngLine();
                                ((MindState)state).getAnswerDisplay().loadWords(line);
                                target.setOpacity(state.getSpriteLibrary(), 1.0f);
                                children = new ArrayList<>();
                                children.add(target);
                                for (int j = 0; j < inPool.length; j++){
                                    inPool[j] = false;
                                }
                                HistoryItem item = new HistoryItem(target, poolLines);
                                history.add(item);
                                poolLines = LinesCatalog.getStreakLinesFromIds(target.getLine().getStreakLines());
                                setCanPopulate(true);
                            }else{
                                children = new ArrayList<>();
                                if (!history.isEmpty()) {
                                    poolLines = history.get(history.size() - 1).getPlayerLines();                                        
                                    history.remove(history.size() - 1);
                                    if (!history.isEmpty()) {
                                        PoolItem poolItem = history.get(history.size() - 1).getPoolItem();
                                        children.add(poolItem);
                                        for (int j = 0; j < inPool.length; j++){
                                            inPool[j] = false;
                                        }
                                        selectedLine = poolItem.getLine();
                                    }else{
                                        selectedLine = null;
                                    }
                                }
                                state.getAnswerDisplay().removeLastAdded(state);
                                setCanPopulate(true);
                                loadGraphics(state.getSpriteLibrary());
                            }
                        }
                    }
                });
                int posX = random.nextInt((int)spawningZone.getWidth() - spawnItem.getSize().getWidth()) + (int)spawningZone.getLocation().getX();
                // System.out.println("random posX = " + posX);
                int posY = random.nextInt((int)spawningZone.getHeight() - spawnItem.getSize().getHeight()) + (int)spawningZone.getLocation().getY();
                // System.out.println("random posY = " + posY);
                Rectangle testArea = new Rectangle(posX-10, posY-10, spawnItem.getSize().getWidth()+10, spawnItem.getSize().getHeight()+10);
                // System.out.println("testArea rectangle: " + testArea.toString());
                boolean intersects = false;
                for (GameObject poolItem : children) {
                    if (poolItem instanceof PoolItem) {
                        int tx = (int)testArea.getLocation().getX(); 
                        int ty = (int)testArea.getLocation().getY();
                        int tw = (int)testArea.getWidth();
                        int th = (int)testArea.getHeight();
                        int rx = (int)((PoolItem)poolItem).getArea().getLocation().getX();
                        int ry = (int)((PoolItem)poolItem).getArea().getLocation().getY();
                        int rw = (int)((PoolItem)poolItem).getArea().getWidth();
                        int rh = (int)((PoolItem)poolItem).getArea().getHeight();
                        if ((rw > tx) && (rh > ty) && (tw > rx) && (th > ry)) {
                            // System.out.println("poolItem rectangle: " + ((PoolItem)poolItem).getArea().toString());
                            intersects = true;
                            // System.out.println("intersect true");
                            break;
                        }
                    }
                }
                
                if (!intersects) {
                    // System.out.println("doesn't intersect");
                    inPool[chosenNumber] = true;
                    spawnItem.updatedPosX(posX);
                    spawnItem.updatedPosY(posY);
                    spawnItem.setIndexNumber(chosenNumber);
                    spawnItem.setArea(testArea);
                    spawnItem.setLine(currentPlayerLine);
                    spawnItem.setAnswerPool(this);
                    children.add(spawnItem);
                    spawnItem.fadeIn(0.01f);
                }
            }else{
            }
        }

    }

    @Override
    public void update(State state) {
        super.update(state);
        if (canPopulate || children.isEmpty()) {
            populate(poolLines, ((MindState)state));
            canPopulate = false;
        }
        loadGraphics(state.getSpriteLibrary());
        
    }

    @Override
    public void loadGraphics(SpriteLibrary spriteLibrary) {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(new Size(1600, 900), ImageUtils.ALPHA_BLEND);
        Graphics2D graphics = image.createGraphics();
        // graphics.setColor(Color.BLUE);
        // graphics.drawRect((int)spawningZone.getLocation().getX(), (int)spawningZone.getLocation().getY(), (int)spawningZone.getSize().getWidth()-1, (int)spawningZone.getSize().getHeight()-1);
        // graphics.setColor(Color.RED);
        // graphics.drawRect(children.get(0).getPosition().getIntX(), children.get(0).getPosition().getIntY(), children.get(0).getSize().getWidth(), children.get(0).getSize().getWidth());
        for (GameObject gameObject : children) {
            graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().getIntX(),
                gameObject.getPosition().getIntY(),
                null
            );
        }
        graphics.dispose();
        Image result = image;
        sprite = result;
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    public void removePoolItems(){
        children.removeAll(toRemove);
    }

    public List<PlayerLine> getPoolLines() {
        return poolLines;
    }

    public void setPoolLines(List<PlayerLine> poolLines) {
        this.poolLines = poolLines;
    }

    public boolean[] getInPool() {
        return inPool;
    }

    public List<GameObject> getToRemove() {
        return toRemove;
    }

    public void setCanPopulate(boolean canPopulate) {
        this.canPopulate = canPopulate;
    }

    public PlayerLine getSelectedLine() {
        return selectedLine;
    }

    private class HistoryItem{
        private List<PlayerLine> playerLines;
        private PoolItem poolItem;

        public HistoryItem(PoolItem poolItem, List<PlayerLine> lines){
            this.poolItem = poolItem;
            this.playerLines = lines;
        }

        public List<PlayerLine> getPlayerLines() {
            return playerLines;
        }

        public PoolItem getPoolItem() {
            return poolItem;
        }
    }
}
