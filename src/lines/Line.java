package lines;

import java.util.ArrayList;
import java.util.List;

import state.counter.StagesEnum;

public class Line {
    private int id;
    private List<Integer> parentId;
    private int nodeLevel;
    private StagesEnum stage;
    private boolean initialLine;
    private List<Tags> tags;
    private List<String> engLine;
    private List<String> ptLine;
    private List<Integer> reponsesId;
    // private List<Line> responses;

    public Line(){
        parentId = new ArrayList<>();
        tags = new ArrayList<>();
        engLine = new ArrayList<>();
        ptLine = new ArrayList<>();
        reponsesId = new ArrayList<>();
        // responses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }


    public List<Integer> getParentId() {
        return parentId;
    }


    public int getNodeLevel() {
        return nodeLevel;
    }


    public StagesEnum getStage() {
        return stage;
    }


    public List<Tags> getTags() {
        return tags;
    }

    public List<String> getEngLine() {
        return engLine;
    }

    public List<String> getPtLine() {
        return ptLine;
    }

    public List<Integer> getReponsesId() {
        return reponsesId;
    }

    // public List<Line> getResponses() {
    //     return responses;
    // }

    public boolean isInitialLine() {
        return initialLine;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public void setStage(StagesEnum stage) {
        this.stage = stage;
    }

    public void setInitialLine(boolean initialLine) {
        this.initialLine = initialLine;
    }


    public class Tags{
        private String name;
        private boolean valid;
    }
}
